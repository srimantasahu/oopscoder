<?php

if ( ! function_exists( 'generate_setup' ) ) {

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
