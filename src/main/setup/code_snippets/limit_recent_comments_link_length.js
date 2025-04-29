document.addEventListener('DOMContentLoaded', function() {
    const commentLinks = document.querySelectorAll('a.wp-block-latest-comments__comment-link');

    commentLinks.forEach(function(link) {
        let originalText = link.textContent;
        if (originalText.includes(':')) {
            let trimmedText = originalText.split(':')[0]; // Get text before first colon
            link.textContent = '`' + trimmedText.trim() + '`';
        }
    });
});
