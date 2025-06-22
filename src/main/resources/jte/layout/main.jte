@param gg.jte.Content content

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recepção Clínica</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" />
</head>
<body>
<video autoplay muted loop id="bg-video">
  <source src="img/heartpulse.mp4" type="video/mp4">
  Seu navegador não suporta vídeos HTML5.
</video>
    @template.layout.header()
    <div class="wrapper">
        <main class="main container">
            ${content}
        </main>
    </div>
    @template.layout.footer()
<script>
document.addEventListener('DOMContentLoaded', function() {
    const menuToggle = document.getElementById('menu-toggle');
    const headerNav = document.querySelector('.header-nav');
    
    // Criar overlay dinamicamente
    const overlay = document.createElement('div');
    overlay.className = 'mobile-overlay';
    document.body.appendChild(overlay);
    
    menuToggle.addEventListener('click', function() {
        headerNav.classList.toggle('active');
        overlay.classList.toggle('active');
        
        // Alternar ícone do menu (☰/✕)
        if (headerNav.classList.contains('active')) {
            menuToggle.innerHTML = '&times;';
        } else {
            menuToggle.innerHTML = '&#9776;';
        }
    });
    
    // Fechar menu ao clicar no overlay ou em um link
    overlay.addEventListener('click', closeMenu);
    
    document.querySelectorAll('.header-nav a').forEach(link => {
        link.addEventListener('click', closeMenu);
    });
    
    function closeMenu() {
        headerNav.classList.remove('active');
        overlay.classList.remove('active');
        menuToggle.innerHTML = '&#9776;';
    }
    
    // Fechar menu ao redimensionar para desktop
    window.addEventListener('resize', function() {
        if (window.innerWidth > 768) {
            closeMenu();
        }
    });
});
</script>
</body>
</html>