// Replace "Read more" with "Continue reading 'Post Title (before colon)'" in GeneratePress excerpts
add_filter( 'generate_excerpt_more_output', 'oopscoder_custom_read_more' );
function oopscoder_custom_read_more() {
    // Get the full post title
    $full_title = the_title_attribute( [ 'echo' => false ] );
    // Extract only the text before the first colon
    $parts = explode( ':', $full_title );
    $title = '<i>' . esc_html( trim( $parts[0] ) ) . '</i>';
    // Build your custom link text
    $text = sprintf(
        /* translators: %s: post title before colon */
        esc_html__( 'Continue reading `%s`', 'oopscoder' ),
        $title
    );
    // Return the ellipsis + our custom link
    return sprintf(
        '… <a class="read-more" href="%1$s" title="%2$s">%3$s</a>',
        esc_url( get_permalink() ),
        esc_attr( $title ),
        $text
    );
}
