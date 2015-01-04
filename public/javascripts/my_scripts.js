function showValues() {
	var fields = $(":input").serializeArray();
	$("#results").empty();
	jQuery.each(fields, function(i, field) {
		$("#results").append("<br>" + field.name + ": " + field.value);
	});
}
$(":text").keyup(showValues);
$(":input").keyup(showValues);
$("select").change(showValues);
$("radio").change(showValues);
showValues();