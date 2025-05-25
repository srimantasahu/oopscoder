function custom_excerpt_length($length) {
    return 40; // Set to your desired word count (e.g., 40 words)
}
add_filter('excerpt_length', 'custom_excerpt_length');
