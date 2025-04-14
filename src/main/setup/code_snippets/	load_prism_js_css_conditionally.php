function lazyload_prism_assets() {
    if (is_single()) {
        wp_enqueue_style('prism-css', 'https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/themes/prism.min.css');
        wp_enqueue_script('prism-js', 'https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js', [], null, true);
    }
}
add_action('wp_enqueue_scripts', 'lazyload_prism_assets');
