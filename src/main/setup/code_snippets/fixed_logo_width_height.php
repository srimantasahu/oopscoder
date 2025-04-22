add_filter( 'get_custom_logo', 'add_logo_dimensions' );
function add_logo_dimensions( $html ) {
    $html = str_replace( '<img', '<img width="200" height="60"', $html );
    return $html;
}
