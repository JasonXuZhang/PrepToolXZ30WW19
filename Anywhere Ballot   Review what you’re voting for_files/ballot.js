var view, height, space, hold, value, text, all = 'touchstart touchmove touchend mouseup mouseout', amount = 200, write;
var $voting, $page, $keyboard, $description, $content, $options, $object, $writing, $written, $progress, $review, $c, $counter, $info, $error;
var Bsettings, Bhelp, Breview, Bcancel, Baccept, Bskip, Bnext, Bback;

if ( Ballot_Localize.tablet ) {
	var click = 'touchstart', release = 'touchend';
} else if ( Ballot_Localize.mobile ) {
	var click = 'click', release = 'click';
} else {
	var click = 'mousedown', release = 'mouseup';
}

if ( Ballot_Localize.limit ) {
	limit = Ballot_Localize.limit;
} else {
	limit = 1;
}

function init_all() {

	if ( Ballot_Localize.tablet ) {
		jQuery(document).delegate('body','touchmove',function(e){
		    e.preventDefault();
		}).delegate('.scroll','touchmove',function(){
		    e.stopPropagation();
		});
	}

	$c = jQuery('#the-counter');
	$counter = jQuery('#counter');
	$page = jQuery('#page');
	$info = jQuery('#info');
	$error = jQuery('#error');
	$review = jQuery('#review');
	$progress = jQuery('#progress');
	Bsettings = jQuery('#button-settings');
	Bsettings.bind(click, function() {
		info_pane_display('settings');
	});
	Bhelp = jQuery('#button-help');
	Bhelp.bind(click, function() {
		info_pane_display('help');
	});
	Breview = jQuery('#button-review');
	Breview.unbind().bind(release, function() { destroy_all(); ballot_ajax( 0 ); });
	Bcancel = jQuery('#button-cancel');
	Baccept = jQuery('#button-accept');
	Bskip = jQuery('#button-skip');
	Bskip.unbind().bind(release, function() {
		destroy_all();
		if ( Ballot_Localize.flag == 'vote' ) {
			ballot_ajax( 1 );
		} else {
			document.location.href = Ballot_Localize.next;
		}
	});
	Bnext = jQuery('#button-next');
	Bnext.unbind().bind(release, function() {
		destroy_all();
		if ( Ballot_Localize.flag == 'vote' ) {
			ballot_ajax( 1 );
		} else {
			document.location.href = Ballot_Localize.next;
		}
	});
	Bback = jQuery('#button-back');
	Bback.unbind().bind(release, function() {
		destroy_all();
		if ( Ballot_Localize.flag == 'review' ) {
			document.location.href = Ballot_Localize.refer;
		} else 	if ( Ballot_Localize.flag == 'vote' ) {
			ballot_ajax( -1 );
		} else {
			document.location.href = Ballot_Localize.back;
		}
	});
	Bbegin = jQuery('#button-begin');
	Bbegin.unbind().bind(release, function() { destroy_all(); document.location.href = Ballot_Localize.next; });
	Bcast = jQuery('#button-cast');
	Bcast.unbind().bind(release, function() { destroy_all(); document.location.href = Ballot_Localize.next; });
	Breturn = jQuery('#button-return');
	Breturn.unbind().bind(release, function() { destroy_all(); document.location.href = Ballot_Localize.back; });
	Breturnreview = jQuery('#button-return-review');
	Breturnreview.unbind().bind(release, function() { destroy_all(); ballot_ajax( 0 ); });
	Bvote = jQuery('#button-vote');
	Bvote.unbind().bind(release, function() { destroy_all(); TEST_DESTROY_DATA(); });
	scroll_pad_init();
	vote_option_init();
	write_in_init();
	info_pane_init();
	error_pane_init();
	init_info_buttons();
	button_check();
	jQuery('body').removeClass('loading');
}

function ballot_ajax( d ) {
	choices = jQuery('.active').map( function() {
		if ( this.id.indexOf('write') !== -1 ) {
			return this.id + ':' + jQuery('.desc p', this).text();
		}
		return this.id;
	} ).get();
	race = jQuery('.container', $voting ).attr('id');
	jQuery.post( Ballot_Localize.url, { action: 'ballot_ajax', choices: choices, race: race, direction: d, nonce: Ballot_Localize.nonce, rand: Ballot_Localize.rand }, function( response ) {
		if ( response.success ) {
			if ( d == 1 ) {
				document.location.href = Ballot_Localize.next;
//				jQuery('#ajax-container').load( Ballot_Localize.next + ' #ajax', function() {
//					destroy_all();
//					init_all();
//				});
			} else if ( d == -1 ) {
				document.location.href = Ballot_Localize.back;
//				jQuery('#ajax-container').load( Ballot_Localize.next + ' #ajax', function() {
//					destroy_all();
//					init_all();
//				});
			} else if ( d == 0 ) {
				document.location.href = Ballot_Localize.review;
//				jQuery('#ajax-container').load( Ballot_Localize.next + ' #ajax', function() {
//					destroy_all();
//					init_all();
//				});
			}
		}
	});
}

function TEST_DESTROY_DATA() {
	jQuery.post( Ballot_Localize.url, { action: 'ballot_ajax_destroy', nonce: Ballot_Localize.nonce }, function( response ) {
		if ( response.success ) {
			 document.location.href = Ballot_Localize.next;
		}
	});
}

function destroy_all() {
	$progress.hide();
	Bsettings.hide();
	Bhelp.hide();
	Breview.hide();
	Breturnreview.hide();
	Bcancel.hide();
	Baccept.hide();
	Bskip.hide();
	Bnext.hide();
	Bback.hide();
	Bcast.hide();
	$voting.hide();
	$review.hide();
	$page.hide();
	$keyboard.hide();
	$info.hide();
	$error.hide();
	jQuery('body').addClass('loading');
}

function scroll_pad_init() {
	$description = jQuery('#description');
	$description.height('auto');
	$content = jQuery('#content');
	$content.height('auto');
	$options = jQuery('#options');
	$options.height('auto');
	height = $description.height() + $content.height() + $options.height();
	view = jQuery(window).height() - 200;
	if ( height > view ) {
		jQuery('.scroll').each(function(i, v) {
			$pad = jQuery(v);
			space = view - ( height - $pad.height() ) - 162;
			if ( space < amount ) {
				scroll_pad_check($pad);
				$pad.addClass('override');
				$pad.prev().unbind().hide();
				$pad.next().unbind().hide();
			} else {
				$pad.height( space );
				scroll_pad_check($pad);
				$pad.removeClass('override');
				$pad.scroll( function() { scroll_pad_check($pad); });
				$pad.prev().unbind().show().bind(click, function(e) {
					scroll_pad_scroll('up', $pad);
				}).bind(release, function(e) {
					clearTimeout(hold);
				}).bind(all, function(e) {
					e.preventDefault();
				});
				$pad.next().unbind().show().bind(click, function(e) {
					scroll_pad_scroll('down', $pad);
				}).bind(release, function(e) {
					clearTimeout(hold);
				}).bind(all, function(e) {
					e.preventDefault();
				});
			}
		});
	} else {
		jQuery('.scroll').each(function(i, v) {
			$pad = jQuery(v);
			scroll_pad_check($pad);
			$pad.addClass('override');
			$pad.prev().unbind().hide();
			$pad.next().unbind().hide();
		});
	}
}

function scroll_pad_scroll(d, p) {
	if (d == 'up') {
		p.stop().animate({ scrollTop: p.scrollTop() - amount }, 400, 'linear');
	} else if (d == 'down') {
		p.stop().animate({ scrollTop: p.scrollTop() + amount }, 400, 'linear');
	} else { return false; }
	hold = setTimeout(scroll_pad_scroll, 400, d, p );
}

function scroll_pad_check(p) {
	if( p.scrollTop() == 0 ) {
		p.removeClass('top').prev().removeClass('yellow').addClass('dead');
	} else if ( p.prev().hasClass('dead') ) {
		p.addClass('top').prev().removeClass('dead').addClass('yellow');
	}
	if( p.scrollTop() + p.height() == p.prop('scrollHeight') ) {
		p.removeClass('bottom').next().removeClass('yellow').addClass('dead');
	} else if ( p.next().hasClass('dead') ) {
		p.addClass('bottom').next().removeClass('dead').addClass('yellow');
	}
}

function vote_option_init() {
	$voting = jQuery('#voting');
	$keyboard = jQuery('#keyboard');
	button_check();
	if ( Ballot_Localize.flag == 'review' || Ballot_Localize.flag == 'reviewing' && $review.length > 0 ) {
		jQuery('.review').bind(click, function(e) {
			$object = jQuery(this);
			destroy_all();
			document.location.href = $object.data('review');
		});
	} else {
		jQuery('.choice').bind(click, function(e) {
			$object = jQuery(this);
			if ( jQuery('.active').length < limit ) {
				if ( $object.hasClass('active') ) {
					$object.removeClass('active');
					button_check();
				} else {
					if ( $object.hasClass('open-keyboard') ) {
						$written = jQuery('.written', $object);
						$writing.addClass( $written.attr('id') );
						text = $written.text();
						if ( text == 'Touch here to write in another pair of names') {
							write = 'Touch here to write in another pair of names';
						} else {
							write = 'Touch here to write in another name';
						}
						Bnext.hide();
						Breturnreview.hide();
						if ( text !== write ) {
							$writing.attr('value', text + '_');
							Bskip.hide(0, function() {
								Baccept.show();
							});
						} else {
							Bskip.hide();
						}
						$voting.hide(0, function() {
							$keyboard.show();
						});
						Bback.hide(0, function() {
							Bcancel.show();
						});
					} else {
						$object.addClass('active');
						button_check();
					}
				}
			} else {
				if ( $object.hasClass('active') ) {
					$object.removeClass('active');
					button_check();
				} else {
					error_pane_display('vote-error');
				}
			}
		});
	}
}

function button_check() {
	if ( Ballot_Localize.flag == 'how' ) {
		Bback.show();
		Bnext.show();
	}
	if ( Ballot_Localize.flag == 'vote' ) {
		$progress.show();
		Bsettings.show();
		Bhelp.show();
		Breview.show();
		Bback.show();
		if ( jQuery('.active').length > 0 ) {
			Bskip.hide(0, function() {
				Bnext.show();
			});
		} else {
			Bnext.hide(0, function() {
				Bskip.show();
			});
		}
	}
	if ( Ballot_Localize.flag == 'reviewing' && $review.length > 0 ) {
		Bsettings.show();
		Bhelp.show();
		Bcast.show();
	} else if ( Ballot_Localize.flag == 'reviewing' && $voting.length > 0 ) {
		Bsettings.show();
		Bhelp.show();
		Breview.show();
		Breturnreview.show();
	} else if ( Ballot_Localize.flag == 'reviewing' ) {}
	if ( Ballot_Localize.flag == 'review' ) {
		Bback.show();
		Bsettings.show();
		Bhelp.show();
	}
	count = limit - jQuery('.active').length;
	if ( count == 0 ) {
		$c.hide();
	} else {
		$c.show();
	}
	$counter.text(count);
}

function info_pane_init() {
	jQuery('.close, #info').bind(click, function(e) {
		if ( jQuery(e.target).is('.close') || jQuery(e.target).is('#info') || jQuery(e.target).is('.outer') ) {
			jQuery('#info,#info .inner').hide();
		} else {
			e.preventDefault;
			return;
		}
	});
}
function error_pane_init() {
	jQuery('.error-close, #error').bind(click, function(e) {
		if ( jQuery(e.target).is('.error-close') || jQuery(e.target).is('#error') || jQuery(e.target).is('.outer') ) {
			jQuery('#error,#error .inner').hide();
		} else {
			e.preventDefault;
			return;
		}
	});
}

function init_info_buttons() {
	jQuery('.info').bind(click, function() {
		var classList = jQuery(this).attr('class').split(/\s+/);
		for (var i = 0; i < classList.length; i++) {
			if ( classList[i].indexOf('open-') !== -1 ) {
				info_pane_display( classList[i].replace('open-', '') );
			}
		}
	});
}

function info_pane_display(i) {
	if ( jQuery('#' + i).length > 0 ) {
		jQuery('#info,#' + i).show();
	}
}
function error_pane_display(i) {
	if ( jQuery('#' + i).length > 0 ) {
		jQuery('#error,#' + i).show();
	}
}

function write_in_init() {
	$writing = jQuery('#writing');
	Bcancel.bind(release, function(e) {
		$written.closest('.choice').removeClass('active');
		text = $writing.attr('value').replace('_', '');
		if ( text == '' ) {
			$written.text( write );
		} else {
			$written.text( text );
		}
		$writing.attr('class', '');
		$keyboard.hide(0, function() {
			$voting.show();
			scroll_pad_init();
		});
		Bcancel.hide(0, function() {
			button_check();
		});
		Baccept.hide(0, function() {
			button_check();
		});
	});
	Baccept.bind(release, function(e) {
		$written.closest('.choice').addClass('active');
		text = $writing.attr('value').replace('_', '');
		$written.text( text );
		$writing.attr('class', '');
		$writing.attr('value', '_');
		$keyboard.hide(0, function() {
			$voting.show();
			scroll_pad_init();
		});
		Bcancel.hide(0, function() {
			button_check();
		});
		Baccept.hide(0, function() {
			button_check();
		});
	});
	jQuery('.key').bind(click, function() {
		value = jQuery(this).attr('value');
		text = $writing.attr('value');
		if ( text.length == 51 ) {
			$writing.attr('value', text.substring(0, text.length - 1) + ' ');
		} else if ( value == 'Space' ) {
			$writing.attr('value', text.substring(0, text.length - 1) + ' _');
		} else if ( value.indexOf('Erase') == -1 ) {
			$writing.attr('value', text.substring(0, text.length - 1) + value + '_');
		}
		if ( value.indexOf('Erase') !== -1 ) {
			$writing.attr('value', text.substring(0, text.length - 2) + '_');
		}
		if ( $writing.attr('value').length > 1 ) {
			Baccept.show();
		} else {
			Baccept.hide();
		}
	});
}

jQuery('document').ready(function($) {
	init_all();
	$(window).resize(function() {
		scroll_pad_init();
	});
});