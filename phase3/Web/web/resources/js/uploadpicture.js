/**
 * Created by Jerrel on 11/21/16.
 */
document.getElementById('getval').addEventListener('change', readURL, true);
function readURL(){
    var file = document.getElementById("getval").files[0];
    var reader = new FileReader();
    reader.onloadend = function(){
        document.getElementById('profilePicture').style.backgroundImage = "url(" + reader.result + ")";
    }
    if(file){
        reader.readAsDataURL(file);
    }else{
    }
}