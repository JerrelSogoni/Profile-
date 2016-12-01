/**
 * Created by jerre on 11/25/2016.
 */
// Write JavaScript here $(selector, context)

$('.dropdown-menu a').click(function(){
    $('#selected').text($(this).text());
});