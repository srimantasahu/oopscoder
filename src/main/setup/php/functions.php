<?php

if ( ! function_exists( 'generate_setup' ) ) {

    function generate_setup() {

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
            © <?php echo date('Y'); ?> OopsCoder.com • All rights reserved • <a href="/privacy-policy">Privacy</a>
        </div>
        <?php
    }
}
