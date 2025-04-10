<?php

if ( ! function_exists( 'generate_setup' ) ) {

    function generate_setup() {

        // Added towards the end - to suppress SyntaxHighlighter Evolved
        // turning double quotes (") into &quot; in the Java code area
        remove_filter('the_content', 'wptexturize');
    }

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
