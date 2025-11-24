function custom_excerpt_length($length) {
    return 26; // Set to your desired word count (e.g., 26 words)
}
add_filter('excerpt_length', 'custom_excerpt_length');
