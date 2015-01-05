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


jQuery(document).ready(function($) {
      $(".clickableRow").click(function() {
            window.document.location = $(this).attr("href");
      });
});


function getDistance(data){
console.log("sd");
	console.log(data);

return [1,2]
}




