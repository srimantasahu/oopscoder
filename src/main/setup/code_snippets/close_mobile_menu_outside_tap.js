document.addEventListener('DOMContentLoaded', function () {
  const nav = document.querySelector('.main-navigation');
  const menuToggle = document.querySelector('.menu-toggle');

  // Watch for clicks anywhere on the page
  document.addEventListener('click', function (e) {
    const isToggle = menuToggle && menuToggle.contains(e.target);
    const isNav = nav && nav.contains(e.target);

    // If menu is open, and click is outside both toggle and nav, close it
    if (nav.classList.contains('toggled') && !isToggle && !isNav) {
      menuToggle.click(); // Triggers the built-in toggle handler
    }
  });
});
