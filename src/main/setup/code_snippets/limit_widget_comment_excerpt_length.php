// Trim comment excerpts in the widget
add_filter('get_comment_excerpt', function($excerpt, $comment_ID) {
    return wp_trim_words(get_comment($comment_ID)->comment_content, 15);
}, 10, 2);