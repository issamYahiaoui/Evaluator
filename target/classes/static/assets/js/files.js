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


function handleFileSelect(fileInputSelector)
{
    if (!window.File || !window.FileReader || !window.FileList || !window.Blob) {
        alert('The File APIs are not fully supported in this browser.');
        return;
    }

    input = $(fileInputSelector)[0];
    if (!input.files) {
        alert("This browser doesn't seem to support the `files` property of file inputs.");
    }
    else if (!input.files[0]) {
        alert("Please select a file before clicking 'Load'");
    }
    else {
        file = input.files[0];
        fr = new FileReader();
        fr.onload = function(){
            console.log('file droped ...!', fileInputSelector)
            var id= fileInputSelector.split('#')[1]
            console.log(id)
            receivedText(id)
            //$('div[name=fileInputSelector]').show();
        };
        fr.readAsText(file);

    }
}

function receivedText(s) {
    var selector = 'div[name='+s+']'
    console.log(file.name)
    $(selector).append( $( '<div/>' ).html( '<li style="margin: 5px; text-decoration: none" class="" >\n' +
        '                                <a href="#" class="btn btn-outline-primary btn-sm \tm-btn m-btn--icon m-btn--pill">\n' +
        '\t\t\t\t\t\t\t\t\t<span>\n' +
        '\t\t\t\t\t\t\t\t\t\t<i class="fa flaticon-file"></i>\n' +
        '\t\t\t\t\t\t\t\t\t\t<span>'+file.name+'</span>\n' +
        '\t\t\t\t\t\t\t\t\t</span>\n' +
        '                                </a>\n' +
        '                                <a onclick="showPdf(file)" href="#"   class="btn btn-outline-danger btn-sm \tm-btn m-btn--icon m-btn--pill"' +
        '' +
        '  data-toggle="modal" data-target="#preview" >\n' +
        '\t\t\t\t\t\t\t\t\t<span>\n' +

        '\t\t\t\t\t\t\t\t\t\t<i class="fa flaticon-medical" ></i>\n' +
        '\t\t\t\t\t\t\t\t\t</span>\n' +
        '                                </a>\n' +
        '                            </li>' ))
}

showPdf = function(){
    console.log('show pdf')


    var object = "<object data=\"{fileName}\" type=\"application/pdf\" width=\"750px\" height=\"550px\">";
                //object += "If you are unable to view file, you can download from <a href = \"/{fileName}\">here</a>";
                //object += " or download <a target = \"_blank\" href = \"http://get.adobe.com/reader/\">Adobe PDF Reader</a> to view the file.";
                object += "</object>";
                object = object.replace(/{fileName}/g, "/assets/app/media/img/users/test.pdf");
     $("#preview").html(object);

    }
}
$(function(){
    $("#folder_form a").on('click',function () {
        var name =$(this).attr('name');
        $("#"+name).click()
        console.log(" clicked ... !",name)
        handleFileSelect("#"+name)

    })

})