var view, height, space, hold, value, text, all = 'touchstart touchmove touchend mouseup mouseout', amount = 200, write;
var $voting, $page, $keyboard, $description, $content, $options, $object, $writing, $written, $progress, $review, $c, $counter, $info, $error;
var Bsettings, Bhelp, Breview, Bcancel, Baccept, Bskip, Bnext, Bback;

var currentIndex, questionCount, questions;

if ( Ballot_Localize.tablet ) {
	var click = 'touchstart', release = 'touchend';
} else if ( Ballot_Localize.mobile ) {
	var click = 'click', release = 'click';
} else {
	var click = 'mousedown', release = 'mouseup';
}

function init_all() {
	$c = jQuery('#the-counter-' + currentIndex);
	$counter = jQuery('#counter-' + currentIndex);
	$page = jQuery('#page');
	$info = jQuery('#info');
	$error = jQuery('#error');
	$review = jQuery('#review');
	$review.hide();
	$reviewSections = jQuery('.review');
	$reviewSections.bind(click, function(e) {
		var reviewId = e.target.id;
		jumpToQuestionByReviewId(reviewId);
	});
	
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
	Breview.unbind().bind(release, function() {
		destroy_all();
		updateReviewSection();
		$review.show();
		Bcast.show();
	});
	Bcancel = jQuery('#button-cancel');
	Baccept = jQuery('#button-accept');
	Bskip = jQuery('#button-skip');
	Bskip.unbind().bind(release, function() {
		next(1);
	});
	Bnext = jQuery('#button-next');
	Bnext.unbind().bind(release, function() {
		next(1);
	});
	Bback = jQuery('#button-back');
	Bback.unbind().bind(release, function() {
		next(-1);
	});
	Bbegin = jQuery('#button-begin');
	Bbegin.unbind().bind(release, function() { destroy_all(); document.location.href = Ballot_Localize.next; });
	Bcast = jQuery('#button-cast');
	Bcast.unbind().bind(release, function() { 
		destroy_all(); 
		var result = '';
		for (var i = 0; i < questionCount; i++) {
			result += 'q' + (i + 1) + ': ' + questions[i].getIds() + '\n';
		}
		alert(result);
	});
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
			} else if ( d == -1 ) {
				document.location.href = Ballot_Localize.back;
			} else if ( d == 0 ) {
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
	for (var i = 1; i <= questionCount; i++) {
		$cur_question = jQuery('#q' + i);
		$cur_question.hide();
	}
	jQuery('body').addClass('loading');
}

function scroll_pad_init() {
	$description = jQuery('#description-' + currentIndex);
	$content = jQuery('#content-' + currentIndex);
	$content.height('auto');
	$options = jQuery('#options-' + currentIndex);
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
	$voting = jQuery('#voting-' + currentIndex);
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
			$currentQuestion = jQuery('#q' + currentIndex);
			$object = jQuery(this);
			var choiceId = $object.attr('id');
			if ( jQuery('.active', $currentQuestion).length < limit ) {
				if ( $object.hasClass('active') ) {
					$object.removeClass('active');
					questions[currentIndex - 1].remove(choiceId);
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
							alert($voting.attr('id'));
							$keyboard.show();
						});
						Bback.hide(0, function() {
							Bcancel.show();
						});
					} else {
						$object.addClass('active');
						questions[currentIndex - 1].add(choiceId);
						button_check();
					}
				}
			} else {
				if ( $object.hasClass('active') ) {
					$object.removeClass('active');
					questions[currentIndex - 1].remove(choiceId);
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
	$currentQuestion = jQuery('#q' + currentIndex);
	count = limit - jQuery('.active', $currentQuestion).length;
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

function init() {
	questionCount = jQuery('#question-count').html();
	currentIndex = 1;
	questions = new Array(questionCount);
	for (var i = 0; i < questionCount; i++) {
		questions[i] = new QuestionData();
	}
	document.getElementById("q1").style.display = "";
	for (var i = 2; i <= questionCount; i++) {
		var divId = "q" + i;
		document.getElementById(divId).style.display = "none";
	}
	$totalCount = jQuery('#progress-total');
	$totalCount.html(questionCount);
	updateQuestionData();
}

function next(increment) {
	if (currentIndex + increment < 1 || currentIndex + increment > questionCount)
		return;
	currentIndex += increment;
	var divId = "q" + currentIndex;
	document.getElementById(divId).style.display = "";
	for (var i = 1; i <= questionCount; i++) {
		var divId = "q" + i;
		if (i == currentIndex)
			continue;
		document.getElementById(divId).style.display = "none";
	}
	$currentPage = jQuery('#progress-current');
	$currentPage.html(currentIndex);
	updateQuestionData();
}

function updateQuestionData() {
	var questionType = jQuery('#question-type-' + currentIndex).text();
	$voting = jQuery('#voting-' + currentIndex);
	if (questionType == 'choose-m-from-n') {
		var limitId = '#question-limit-' + currentIndex;
		$limitValue = jQuery(limitId);
		limit = $limitValue.text();
		$c = jQuery('#the-counter-' + currentIndex);
		$counter = jQuery('#counter-' + currentIndex);
	} else {
		limit = 1;
	}
}

jQuery('document').ready(function($) {
	init();
	init_all();
	$(window).resize(function() {
		scroll_pad_init();
	});
});

function QuestionData() {
	var selectedIds = new Array();
	this.add = function (optionId) {
		if (selectedIds.indexOf(optionId) == -1) {
			selectedIds.push(optionId);
			selectedIds.sort();	
		}
	};
	this.remove = function (optionId) {
		var index = selectedIds.indexOf(optionId);
		if (index != -1) {
			selectedIds.splice(index, 1);
		}
	};
	this.getIds = function() {
		return selectedIds.toString();
	};
	this.getIdCount = function() {
		return selectedIds.length;
	};
	this.getIdsArray = function() {
		return selectedIds;
	};
}

function updateReviewSection() {
	for (var i = 1; i <= questionCount; i++) {
		var question_content = jQuery('#question-' + i).text();
		$review_question = jQuery('#review-question-' + i);
		$review_question.text(question_content);
		var cur_question = questions[i - 1];
		var content = '';
		var count = cur_question.getIdCount();
		if (count == 0) {
			content = '(You did not vote for anyone)';
		} else {
			var ids = cur_question.getIdsArray();
			for (var j in ids) {
				$cur_choice = jQuery('#' + ids[j]);
				content += $cur_choice.text() + '<br>';
			}
		}
		jQuery('#review-answer-' + i).html(content);
	}
}

function jumpToQuestionByReviewId(reviewId) {
	var parts = reviewId.split("-");
	var questionId = parts[2];
	$review.hide();
	Bcast.hide();
	
	$progress.show();
	Breview.show();
	Bnext.show();
	Bback.show();
	$voting.show();
	// $page.show();
	jQuery('body').removeClass('loading');
	currentIndex = parseInt(questionId);
	jQuery('#q' + currentIndex).show();
}
