<!DOCTYPE html>
<html lang="en-US">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>New Ballot</title>

		<link rel="apple-touch-icon-precomposed" href="http://anywhereballot.com/wp-content/themes/ballot/images/app-icon.png">

		<link rel="stylesheet" href="./ballot/style.css" type="text/css" media="screen">
		<link rel="alternate" type="application/rss+xml" title="Anywhere Ballot RSS Feed" href="http://anywhereballot.com/feed/">
		<link rel="alternate" type="application/atom+xml" title="Anywhere Ballot Atom Feed" href="http://anywhereballot.com/feed/atom/">

		<link rel="pingback" href="http://anywhereballot.com/xmlrpc.php">

		<meta name="robots" content="noindex,follow">
		<script type="text/javascript" src="./ballot/jquery.js"></script>
		<script type="text/javascript" src="./ballot/jquery-migrate.min.js"></script>
		<script type="text/javascript">
			/* <![CDATA[ */
			var Ballot_Localize = {
				"url" : "http:\/\/anywhereballot.com\/wp-admin\/admin-ajax.php",
				"nonce" : "2c0968ed8a",
				"tablet" : "",
				"mobile" : "",
				"limit" : null,
				"next" : "http:\/\/anywhereballot.com\/race\/president-and-vice-president-of-the-united-states\/",
				"back" : "http:\/\/anywhereballot.com\/how\/",
				"review" : "http:\/\/anywhereballot.com\/review\/",
				"refer" : "http:\/\/anywhereballot.com\/how\/",
				"flag" : "vote",
				"rand" : "1125942962"
			};
			/* ]]> */
		</script>
		<script type="text/javascript" src="./ballot/DataStructures.js"></script>
		<script type="text/javascript" src="./ballot/ballot.js"></script>
		<link rel="EditURI" type="application/rsd+xml" title="RSD" href="http://anywhereballot.com/xmlrpc.php?rsd">
		<link rel="wlwmanifest" type="application/wlwmanifest+xml" href="http://anywhereballot.com/wp-includes/wlwmanifest.xml">
		<link rel="next" title="President and Vice President of the United States" href="http://anywhereballot.com/race/president-and-vice-president-of-the-united-states/">
		<meta name="generator" content="WordPress 4.0.1">
		<link rel="canonical" href="./ballot/Anywhere Ballot   Straight Party Vote.html">
		<link rel="shortlink" href="http://anywhereballot.com/?p=6">

		<link rel="shortcut icon" href="http://anywhereballot.com/wp-content/themes/ballot/favicon.ico">
		<!--[if lt IE 9]>
		<link rel="stylesheet" href="http://anywhereballot.com/wp-content/themes/ballot/ie.css" type="text/css" media="all" />
		<![endif]-->
	</head>
	<body>
		<div id="questions-info" style="display: none">
			<span id="question-count">${raceCount}</span>
		</div>
		<section id="header">
			<header class="container">
				<!--			<input id="button-settings" type="button" class="button icon settings open-settings" value="Settings" style="display: inline-block;">
				<input id="button-help" type="button" class="button icon help open-help" value="Help" style="display: inline-block;">
				-->
				<input id="button-review" type="button" class="button review" value="Review your votes" style="display: inline-block;">
			</header>
		</section>
		<#assign i = 1>
		<#list raceList as curRace>
		
		<div id=${"q" + i}>
			<div id=${"question-info-" + i} style="display: none">
				<span id=${"question-type-" + i}>choose-m-from-n</span>
				<span id=${"question-limit-" + i}>${curRace.maxAnswer}</span>
			</div>
			<section id=${"voting-" + i} class="voting">
				<article id=${"race-" + i} class="container">
					<div id=${"description-" + i} style="height: auto;">
						<h1 class="info open-primary"><strong><span id=${"question-" + i}>${curRace.title}</span></strong></h1>
						<h3 class="open-secondary"><strong>Vote for up to <span id=${"limit-" + i}>${curRace.maxAnswer}</span>. </strong><span id=${"the-counter-" + i}>You can choose <strong><span id=${"counter-" + i} class="red">1</span></strong> more.</span></h3>
					</div>

					<div id=${"content-" + i} style="height: auto;">
						<!--${curRace.description}-->
						<#assign lineCounter = 0>
						<#list curRace.descriptions as line>
							<p>${line}</p>
							<#assign lineCounter = lineCounter + 1>
						</#list>
					</div>

					<input type="button" class="pad up dead" value="Touch to see more parties" style="display: none;">

					<div id="options-1" class="scroll override" style="height: auto;">
						
						<#assign candIndex = 1>
						<#list curRace.candidates as cand>
						<div class="option">
							<div id=${"option-" + i + "-" + candIndex} class="choice">
								<div class="vote"></div>
								<div class="desc">
									<p>
										${cand}
									</p>
								</div>
							</div>
						<!--	<div class="open-party-39"></div> -->
						</div>
							<#assign candIndex = candIndex + 1>
						</#list>
					</div>

					<input type="button" class="pad down dead" value="Touch to see more parties" style="display: none;">

				</article>
			</section>
		</div>

			<#assign i = i + 1>
		</#list>
		<section id="keyboard">
			<article class="container">
				<div id="write-description">
					<h1 class="info open-writein"><strong>Write in a name for County Commissioners</strong></h1>
					<aside class="info open-writein">
						<p>
							Use this screen to vote for a person who is <strong>not</strong> on the ballot.
						</p>
						<p>
							To finish, touch <strong>Accept</strong>. If you change your mind, touch <strong>Cancel</strong>.
						</p>
					</aside>
				</div>
				<div id="write-options">
					<div class="write-option">
						<div class="write-choice">
							<input disabled="disabled" id="writing" value="_" type="text">
						</div>
					</div>
				</div>
				<div id="write-content">
					<div>
						<input class="key" value="A" type="button">
						<input class="key" value="B" type="button">
						<input class="key" value="C" type="button">
						<input class="key" value="D" type="button">
						<input class="key" value="E" type="button">
						<input class="key" value="F" type="button">
					</div>
					<div>
						<input class="key" value="G" type="button">
						<input class="key" value="H" type="button">
						<input class="key" value="I" type="button">
						<input class="key" value="J" type="button">
						<input class="key" value="K" type="button">
						<input class="key" value="L" type="button">
					</div>
					<div>
						<input class="key" value="M" type="button">
						<input class="key" value="N" type="button">
						<input class="key" value="O" type="button">
						<input class="key" value="P" type="button">
						<input class="key" value="Q" type="button">
						<input class="key" value="R" type="button">
					</div>
					<div>
						<input class="key" value="S" type="button">
						<input class="key" value="T" type="button">
						<input class="key" value="U" type="button">
						<input class="key" value="V" type="button">
						<input class="key" value="W" type="button">
						<input class="key" value="X" type="button">
					</div>
					<div>
						<input class="key" value="Y" type="button">
						<input class="key" value="Z" type="button">
						<input class="key double" value="Space" type="button">
						<input class="key" value="." type="button">
						<input class="key" value="���" type="button">
					</div>
					<div>
						<input class="key double" value="��� Erase" type="button">
					</div>
					<br class="clearboth">
				</div>
			</article>
		</section>

		<section id="review">
			<article class="container">
				<div id="description" style="height: auto;">
					<h1 class="info open-review"><strong>Review of your vote</strong></h1>
					<h3 class="open-review"><strong>This screen shows everything you voted for.</strong> Review it carefully. If you are ready to cast your ballot, touch <strong>Cast your vote</strong>.</h3>
				</div>

				<input type="button" class="pad up dead" value="Touch to see more" style="display: none;">
				<div id="options" class="scroll override" style="height: auto;">
					<#assign i = 1>
					<#assign questionLimit = 1>
					<#list raceList as curRace>
					<div class="review" id=${"review-" + i}>
						<h2><strong><span id=${"review-question-" + i}>race question</span></strong></h2>
						<div class="warning">
							<p id=${"review-answer-" + i}>
							</p>
						</div>
					</div>
						<#assign i = i + 1>
					</#list>
					<h2>End of review</h2>
				</div>
				<input type="button" class="pad down dead" value="Touch to see more" style="display: none;">
			</article>
		</section>

		<section id="error">
			<article class="container">
				<div class="outer">
					<div id="vote-error" class="inner">
						<p>
							Before you choose another name, <strong>touch the blue box on the name you don't want</strong>. That box will turn white. Then, touch the choice you do want.
						</p>
						<p>
							This helps make sure you only change your vote when you mean to change it.
						</p>
						<div class="control">
							<input type="button" class="onscreen error-close green" value="Close">
							<br class="clearboth">
						</div>
					</div>
				</div>
			</article>
		</section>

		<section id="footer">
			<footer class="container">
				<input id="button-back" type="button" class="button icon back" value="Back" style="display: inline-block;">
				<input id="button-cancel" type="button" class="button icon cancel" value="Cancel">
				<input id="button-skip" type="button" class="button icon skip" value="Skip" style="display: inline-block;">
				<div id="progress" style="display: block;">
					<p>
						<strong><span id="progress-current">1</span></strong> of <strong><span id="progress-total">18</span></strong>
					</p>
				</div>
				<input id="button-next" type="button" class="button icon next green" value="Next" style="">
				<input id="button-accept" type="button" class="button icon accept green" value="Accept">
				<input id="button-cast" type="button" class="button cast green" value="Cast your vote">
				<input id="button-return-review" type="button" class="button icon return green" value="Return to review and cast your vote">
			</footer>
		</section>
	</body>
</html>