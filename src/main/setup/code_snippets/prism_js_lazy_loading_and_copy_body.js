document.addEventListener("DOMContentLoaded", function () {
  const lazyBlocks = document.querySelectorAll("pre.lazy-load");

  const observer = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const pre = entry.target;
        if (pre.dataset.loaded === "false") {
          Prism.highlightElement(pre.querySelector("code"));
          pre.setAttribute("data-loaded", "true");
          observer.unobserve(pre);
        }
      }
    });
  });

  lazyBlocks.forEach(block => observer.observe(block));
});
