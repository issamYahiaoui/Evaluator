
$(function() {


    toast()




});


function toast(){
    msg =  $('.flash').text()
    if(msg){
        toastr.success(msg)
    }

}