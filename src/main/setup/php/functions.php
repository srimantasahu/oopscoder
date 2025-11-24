<?php
/**
 * GeneratePress.
 *
 * Please do not make any edits to this file. All edits should be done in a child theme.
 *
 * @package GeneratePress
 */

if ( ! defined( 'ABSPATH' ) ) {
	exit; // Exit if accessed directly.
}

// Set our theme version.
define( 'GENERATE_VERSION', '3.6.0' );

if ( ! function_exists( 'generate_setup' ) ) {
	add_action( 'after_setup_theme', 'generate_setup' );
	/**
	 * Sets up theme defaults and registers support for various WordPress features.
	 *
	 * @since 0.1
	 */
	function generate_setup() {
		// Make theme available for translation.
		load_theme_textdomain( 'generatepress' );

		// Add theme support for various features.
		add_theme_support( 'automatic-feed-links' );
		add_theme_support( 'post-thumbnails' );
		add_theme_support( 'post-formats', array( 'aside', 'image', 'video', 'quote', 'link', 'status' ) );
		add_theme_support( 'woocommerce' );
		add_theme_support( 'title-tag' );
		add_theme_support( 'html5', array( 'search-form', 'comment-form', 'comment-list', 'gallery', 'caption', 'script', 'style' ) );
		add_theme_support( 'customize-selective-refresh-widgets' );
		add_theme_support( 'align-wide' );
		add_theme_support( 'responsive-embeds' );

		$color_palette = generate_get_editor_color_palette();

		if ( ! empty( $color_palette ) ) {
			add_theme_support( 'editor-color-palette', $color_palette );
		}

		add_theme_support(
			'custom-logo',
			array(
				'height' => 70,
				'width' => 350,
				'flex-height' => true,
				'flex-width' => true,
			)
		);

		// Register primary menu.
		register_nav_menus(
			array(
				'primary' => __( 'Primary Menu', 'generatepress' ),
			)
		);

		/**
		 * Set the content width to something large
		 * We set a more accurate width in generate_smart_content_width()
		 */
		global $content_width;
		if ( ! isset( $content_width ) ) {
			$content_width = 1200; /* pixels */
		}

		// Add editor styles to the block editor.
		add_theme_support( 'editor-styles' );

		$editor_styles = apply_filters(
			'generate_editor_styles',
			array(
				'assets/css/admin/block-editor.css',
			)
		);

		add_editor_style( $editor_styles );

		// Remove Wordpress Admin Bar for Non-Admins
        if (!current_user_can('administrator')) {
            add_filter('show_admin_bar', '__return_false');
        }

        // Added for removing &amp; text for & in code
        remove_filter('the_content', 'wptexturize');
	}

	// Added for loging page logo
	function custom_login_logo() {
		?>
		<style type="text/css">
			body.login div#login h1 a {
				background-image: url('https://oopscoder.com/wp-content/uploads/2025/04/login_logo.png');
				background-size: cover;
				background-position: center;
				width: 100px;
				height: 100px;
				border-radius: 50%;
				display: block;
				margin: 0 auto 20px;
			}
		</style>
		<?php
	}
	add_action('login_head', 'custom_login_logo');

	// Optional: Change login link URL and title
	add_filter('login_headerurl', function() { return home_url(); });
	add_filter('login_headertext', function() { return get_bloginfo('name'); });

	add_filter( 'generate_get_the_title_parameters', function( $params ) {
    // Target non-singular pages: home / archive / blog listing
		if ( ! is_singular() ) {
			$params = array(
				'before' => sprintf(
					'<h3 class="entry-title"%2$s><a href="%1$s" rel="bookmark">',
					esc_url( get_permalink() ),
					'microdata' === generate_get_schema_type() ? ' itemprop="headline"' : ''
				),
				'after' => '</a></h3>',
			);
		}

		return $params;
	} );

	// Added towards the end - for overriding footer notes
	add_filter('generate_copyright', 'custom_generatepress_footer');

	function custom_generatepress_footer() {
        ?>
        <div class="custom-footer">
            © <?php echo date('Y'); ?> OopsCoder • All rights reserved • <a href="/privacy-policy">Privacy Policy</a>
        </div>
        <?php
    }
}

/**
 * Get all necessary theme files
 */
$theme_dir = get_template_directory();

require $theme_dir . '/inc/theme-functions.php';
require $theme_dir . '/inc/defaults.php';
require $theme_dir . '/inc/class-css.php';
require $theme_dir . '/inc/css-output.php';
require $theme_dir . '/inc/general.php';
require $theme_dir . '/inc/customizer.php';
require $theme_dir . '/inc/markup.php';
require $theme_dir . '/inc/typography.php';
require $theme_dir . '/inc/plugin-compat.php';
require $theme_dir . '/inc/block-editor.php';
require $theme_dir . '/inc/class-typography.php';
require $theme_dir . '/inc/class-typography-migration.php';
require $theme_dir . '/inc/class-html-attributes.php';
require $theme_dir . '/inc/class-theme-update.php';
require $theme_dir . '/inc/class-rest.php';
require $theme_dir . '/inc/deprecated.php';

if ( is_admin() ) {
	require $theme_dir . '/inc/meta-box.php';
	require $theme_dir . '/inc/class-dashboard.php';
}

/**
 * Load our theme structure
 */
require $theme_dir . '/inc/structure/archives.php';
require $theme_dir . '/inc/structure/comments.php';
require $theme_dir . '/inc/structure/featured-images.php';
require $theme_dir . '/inc/structure/footer.php';
require $theme_dir . '/inc/structure/header.php';
require $theme_dir . '/inc/structure/navigation.php';
require $theme_dir . '/inc/structure/post-meta.php';
require $theme_dir . '/inc/structure/sidebars.php';
require $theme_dir . '/inc/structure/search-modal.php';
