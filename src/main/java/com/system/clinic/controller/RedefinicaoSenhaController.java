package com.system.clinic.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.system.clinic.entity.RedefinicaoSenhaToken;
import com.system.clinic.repository.RedefinicaoSenhaTokenRepository;
import com.system.clinic.service.UsuarioService;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Controller
public class RedefinicaoSenhaController {

    private static final Logger logger = LoggerFactory.getLogger(RedefinicaoSenhaController.class);

    private final UsuarioService usuarioService;
    private final RedefinicaoSenhaTokenRepository tokenRepository;
    private final JavaMailSender mailSender;

    public RedefinicaoSenhaController(UsuarioService usuarioService,
            RedefinicaoSenhaTokenRepository tokenRepository,
            JavaMailSender mailSender) {
        this.usuarioService = usuarioService;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
    }

    @GetMapping("/esqueci-senha")
    public String mostrarPaginaEsqueciSenha(@RequestParam(required = false) String erro,
            @RequestParam(required = false) String sucesso,
            Model model) {
        model.addAttribute("erro", erro);
        model.addAttribute("sucesso", sucesso);
        return "ForgotPassword/esqueci-senha";
    }

    @PostMapping("/solicitar-redefinicao")
    @Transactional
    public String solicitarRedefinicao(@RequestParam String email,
            RedirectAttributes redirectAttributes) {
        try {
            if (!usuarioService.existeUsuarioPorEmail(email)) {
                redirectAttributes.addFlashAttribute("erro", "E-mail não encontrado.");
                return "redirect:/esqueci-senha";
            }

            // Remove tokens existentes para este e-mail
            tokenRepository.deleteByEmail(email);

            // Cria e salva novo token
            String token = criarTokenRedefinicao(email);

            // Envia e-mail com o link
            enviarEmailRedefinicao(email, token);

            redirectAttributes.addFlashAttribute("sucesso",
                    "Enviamos um e-mail com instruções para redefinir sua senha.");

        } catch (Exception e) {
            logger.error("Erro ao solicitar redefinição para: " + email, e);
            redirectAttributes.addFlashAttribute("erro",
                    "Erro ao processar solicitação. Tente novamente mais tarde.");
        }

        return "redirect:/esqueci-senha";
    }

    private String criarTokenRedefinicao(String email) {
        String token = UUID.randomUUID().toString();
        RedefinicaoSenhaToken redefinicao = new RedefinicaoSenhaToken(
                null, token, email, LocalDateTime.now().plusHours(24));
        tokenRepository.save(redefinicao);
        return token;
    }

    private void enviarEmailRedefinicao(String email, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String resetLink = "https://clinic-web01-c664e6278775.herokuapp.com/redefinir-senha?token=" + token;

            helper.setTo(email);
            helper.setSubject("🔐 Redefinição de Senha - Sistema Clínico");
            helper.setFrom("redefinirsenhasystem@gmail.com");

            String htmlMsg = """
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e2e8f0; border-radius: 8px;">
                        <h2 style="color: #2563eb; text-align: center;">Redefinição de Senha</h2>
                        <p style="margin-bottom: 20px;">Recebemos uma solicitação para redefinir a senha da sua conta. Clique no botão abaixo para continuar:</p>

                        <div style="text-align: center; margin: 25px 0;">
                            <a href="%s" style="display: inline-block; padding: 12px 24px;
                                background-color: #2563eb; color: white; text-decoration: none;
                                border-radius: 6px; font-weight: 500;">Redefinir Senha</a>
                        </div>

                        <p style="font-size: 14px; color: #64748b; margin-top: 20px;">
                            Se você não solicitou esta redefinição, por favor ignore este e-mail.
                        </p>
                        <p style="font-size: 12px; color: #94a3b8; margin-top: 30px; border-top: 1px solid #e2e8f0; padding-top: 15px;">
                            Este link é válido por 24 horas. Se expirar, você precisará solicitar um novo.
                        </p>
                    </div>
                    """
                    .formatted(resetLink);

            helper.setText(htmlMsg, true);
            mailSender.send(message);

            logger.info("E-mail de redefinição enviado com sucesso para: {}", email);
        } catch (Exception e) {
            logger.error("Falha ao enviar e-mail para: {}", email, e);
            throw new RuntimeException("Falha ao enviar e-mail de redefinição", e);
        }
    }

    @GetMapping("/redefinir-senha")
    public String mostrarPaginaRedefinicao(@RequestParam String token, Model model) {
        Optional<RedefinicaoSenhaToken> tokenOpt = tokenRepository.findByToken(token);

        if (tokenOpt.isEmpty()) {
            model.addAttribute("erro", "Token inválido. Solicite um novo link de redefinição.");
            return "erro-token";
        }

        if (tokenOpt.get().getDataExpiracao().isBefore(LocalDateTime.now())) {
            model.addAttribute("erro", "Token expirado. Solicite um novo link de redefinição.");
            return "erro-token";
        }

        model.addAttribute("token", token);
        return "ForgotPassword/nova-senha";
    }

    @PostMapping("/redefinir-senha")
    public String redefinirSenha(@RequestParam String token,
            @RequestParam String novaSenha,
            @RequestParam String confirmacaoSenha,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Valida se as senhas coincidem
        if (!novaSenha.equals(confirmacaoSenha)) {
            model.addAttribute("erro", "As senhas não coincidem.");
            model.addAttribute("token", token);
            return "nova-senha";
        }

        // Valida o token
        Optional<RedefinicaoSenhaToken> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty() || tokenOpt.get().getDataExpiracao().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("erro", "Token inválido ou expirado.");
            return "redirect:/esqueci-senha";
        }

        try {
            // Atualiza a senha
            usuarioService.updatePassword(tokenOpt.get().getEmail(), novaSenha);

            // Remove o token usado
            tokenRepository.delete(tokenOpt.get());

            redirectAttributes.addFlashAttribute("sucesso",
                    "Senha redefinida com sucesso! Você já pode fazer login com sua nova senha.");
            return "redirect:/login";

        } catch (Exception e) {
            logger.error("Erro ao redefinir senha para token: {}", token, e);
            redirectAttributes.addFlashAttribute("erro",
                    "Erro ao redefinir senha. Por favor, tente novamente.");
            return "redirect:/esqueci-senha";
        }
    }
}