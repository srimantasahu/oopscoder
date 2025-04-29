// Limit reply nesting to 2 levels
function wpb_limit_comment_depth( $args ) {
    $args['max_depth'] = 2;
    return $args;
}
add_filter( 'thread_comments_depth_max', 'wpb_limit_comment_depth' );