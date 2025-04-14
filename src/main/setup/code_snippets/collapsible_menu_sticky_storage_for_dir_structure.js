document.addEventListener('DOMContentLoaded', function () {
  const toggles = document.querySelectorAll('.menu-item-has-children > a');

  // Restore menu hierarchy using stored labels
  const openLabels = JSON.parse(localStorage.getItem('oopscoder-open-labels') || '[]');

  toggles.forEach(toggle => {
    const parent = toggle.closest('li');
    const text = toggle.textContent.trim();

    // Add an icon
    const icon = document.createElement('span');
    icon.classList.add('material-icons', 'toggle-icon');
    icon.textContent = openLabels.includes(text) ? '▼' : '▶';  // ▼ down / ▶ right
    parent.insertBefore(icon, toggle);

    // Restore submenu if label was open
    const submenu = parent.querySelector('ul');
    if (submenu && openLabels.includes(text)) {
      submenu.classList.add('open');
      icon.classList.add('open');
    }

    icon.addEventListener('click', function (e) {
      e.preventDefault();
      if (!submenu) return;

      submenu.classList.toggle('open');
      icon.classList.toggle('open');
      icon.textContent = submenu.classList.contains('open') ? '▼' : '▶';

      // Store open menu labels
      const currentlyOpen = Array.from(document.querySelectorAll('.toggle-icon'))
        .filter(i => i.textContent === '▼')
        .map(i => i.nextElementSibling.textContent.trim());
      localStorage.setItem('oopscoder-open-labels', JSON.stringify(currentlyOpen));
    });

    // Save hierarchy before navigation
    toggle.addEventListener('click', function () {
      const currentlyOpen = Array.from(document.querySelectorAll('.toggle-icon'))
        .filter(i => i.textContent === '▼')
        .map(i => i.nextElementSibling.textContent.trim());
      localStorage.setItem('oopscoder-open-labels', JSON.stringify(currentlyOpen));
    });
  });
});
