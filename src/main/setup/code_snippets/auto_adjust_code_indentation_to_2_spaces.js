document.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll('pre code').forEach(function (block) {
    // Split code into lines
    const lines = block.innerHTML.split('\n');

    // Replace leading multiples of 4 spaces with 2 spaces per level
    const adjusted = lines.map(line => {
      return line.replace(/^((?: {4})+)/, match => ' '.repeat((match.length / 4) * 2));
    });

    // Reapply updated code
    block.innerHTML = adjusted.join('\n');

    // Re-highlight using Prism if it's loaded
    if (window.Prism) Prism.highlightElement(block);
  });
});