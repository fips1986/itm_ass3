$(document).ready(function() {
	$(document).tooltip({
		items: "a.hist",
		content: function() {
			var hist = $("<img alt='histogram'>");
			hist.attr("src", $(this).attr("href"));
			hist.css("max-width", "200px")
			return hist;
		}
	});
});