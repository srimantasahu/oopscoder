<?php

// removed comma separator

if ( 'categories' === $item ) {
		$term_separator = apply_filters( 'generate_term_separator', _x( ' ', 'Used between list items, there is a space after the comma.', 'generatepress' ), 'categories' );
		...
}

if ( 'tags' === $item ) {
    $term_separator = apply_filters( 'generate_term_separator', _x( ' ', 'Used between list items, there is a space after the comma.', 'generatepress' ), 'tags' );
    ...
}