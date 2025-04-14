document.addEventListener("DOMContentLoaded", () => {
  const lazyCodes = document.querySelectorAll(".lazy-code");

  const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const pre = entry.target;
        const codeEl = document.createElement("code");
        codeEl.className = pre.dataset.lang || "language-java";

        // Replace encoded line breaks and tabs if any
        const rawCode = pre.dataset.code
          .replace(/\\n/g, "\n")
          .replace(/\\t/g, "\t")
          .replace(/&lt;/g, "<")
          .replace(/&gt;/g, ">")
          .replace(/&amp;/g, "&");

        codeEl.textContent = rawCode; // preserves formatting
        pre.innerHTML = "";
        pre.appendChild(codeEl);
        Prism.highlightElement(codeEl);
        observer.unobserve(pre);
      }
    });
  }, {
    rootMargin: "100px",
  });

  lazyCodes.forEach(el => observer.observe(el));
});
