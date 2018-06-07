console.log('files ...')

hide = function (selector) {
    $(selector).hide()
}

show = function (selector) {
    $(selector).show()
}

triggerFileInput = function (selector) {
    $(selector).click()
}


function handleFileSelect(e)
{
    var name = e.target.id
    console.log('files ... ', e.target.files)
    for (var i = 0; i < e.target.files.length; i++) {
        // var reader = new FileReader();
        // Closure to capture the file information.
            var file = e.target.files[i]

        // reader.onload = function(){
            receivedText(name,file)
        // };
        // Read in the image file as a data URL.
        // reader.readAsText(file);

    }



}

function receivedText(s,file) {
    var selector = 'div[name='+s+']'
    console.log(file.name)
    console.log(selector)
    $(selector).append( $( '<div/>' ).html( '<li style="margin: 5px; text-decoration: none" class="" >\n' +
        '                                <a href="#" class="btn btn-outline-primary btn-sm \tm-btn m-btn--icon m-btn--pill">\n' +
        '\t\t\t\t\t\t\t\t\t<span>\n' +
        '\t\t\t\t\t\t\t\t\t\t<i class="fa flaticon-file"></i>\n' +
        '\t\t\t\t\t\t\t\t\t\t<span>'+file.name+'</span>\n' +
        '\t\t\t\t\t\t\t\t\t</span>\n' +
        '                                </a>\n' +
        '                                <a onclick="'+ showPdf(file.name)+ '" href="#"   class="btn btn-outline-danger btn-sm \tm-btn m-btn--icon m-btn--pill"' +
        '' +
        '  data-toggle="modal" data-target="#preview" >\n' +
        '\t\t\t\t\t\t\t\t\t<span>\n' +

        '\t\t\t\t\t\t\t\t\t\t<i class="fa flaticon-medical" ></i>\n' +
        '\t\t\t\t\t\t\t\t\t</span>\n' +
        '                                </a>\n' +
        '                            </li>' ))
}

function showPdf(filename){
    console.log('show pdf')


    var object = "<object data=\"{fileName}\" type=\"application/pdf\" width=\"750px\" height=\"550px\">";
                //object += "If you are unable to view file, you can download from <a href = \"/{fileName}\">here</a>";
                //object += " or download <a target = \"_blank\" href = \"http://get.adobe.com/reader/\">Adobe PDF Reader</a> to view the file.";
                object += "</object>";
                object = object.replace(/{fileName}/g, "/assets/app/media/img/users/"+filename);
     $("#pdfContent").html(object);

}
$(function(){
    $("#folder_form a").on('click',function () {
        var name =$(this).attr('name');
        $("#"+name).click()
        console.log(" clicked ... !",name)
    })

})