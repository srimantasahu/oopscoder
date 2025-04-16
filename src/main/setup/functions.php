<?php

if ( ! function_exists( 'generate_setup' ) ) {

    function generate_setup() {

        // Added towards the end - to suppress SyntaxHighlighter Evolved
        // turning double quotes (") into &quot; in the Java code area
        remove_filter('the_content', 'wptexturize');
    }

    // Added for loging page logo
	function custom_login_logo() {
		?>
		<style type="text/css">
			body.login div#login h1 a {
				background-image: url('https://oopscoder.com/wp-content/uploads/2025/04/login_logo.png');
				background-size: contain;
				width: 220px;
				height: 100px;
			}
		</style>
		<?php
	}
	add_action('login_head', 'custom_login_logo');

	// Optional: Change login link URL and title
	add_filter('login_headerurl', function() { return home_url(); });
	add_filter('login_headertext', function() { return get_bloginfo('name'); });

	// Added towards the end - for overriding footer notes
	add_filter('generate_copyright', 'custom_generatepress_footer');

	function custom_generatepress_footer() {
        ?>
        <div class="custom-footer">
            © <?php echo date('Y'); ?> oopscoder.com. All rights reserved.
        </div>
        <?php
    }
}
