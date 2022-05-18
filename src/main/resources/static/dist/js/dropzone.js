/**
 *
 * app.js
 *
 */
//function ValidateSize(file) {
//    var FileSize = file.files[0].size / 1024 / 1024;
//    if (FileSize > 1) {
//        Swal.fire({
//            icon: 'error',
//            title: 'Oops...',
//            text: 'File size exceeds 1 MB!',
//            footer: 'Choose file under 1 MB'
//        });
//    } else {
//
//    }
//}
function readFile(input) {
    console.log(input.files)
    var emptyTHis = $(input).parent().parent().parent().find(".dropzone-errortext");
    emptyTHis.empty();
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        var res = input.name.split("formDynamic[");
        var res = res[1].split("][foto_ticket]");
        console.log("THIS:", input)

        var isInputIDAV = document.getElementById("fotoTicketData" + res[0]);
        if (isInputIDAV == null) {
            var create_input = document.createElement('input');
            create_input.setAttribute("type", "file");
            create_input.setAttribute("name", "fotoTicketData");
            create_input.setAttribute("hidden", "true");
            create_input.setAttribute("id", "fotoTicketData" + res[0]);
            create_input.files = input.files;
            document.getElementById("formValidSubmitImage").appendChild(create_input);
        } else {
            var create_input = document.createElement('input');
            create_input.setAttribute("type", "file");
            create_input.setAttribute("hidden", "true");
            create_input.setAttribute("name", "fotoTicketData");
            create_input.setAttribute("id", "fotoTicketData" + res[0]);
            create_input.files = input.files;
            document.getElementById("formValidSubmitImage").appendChild(create_input);
        }

        console.log("input: ", create_input);


        reader.onload = function (e) {
            var FileSize = input.files[0].size / 1024 / 1024;
            if (FileSize < 1) {
                if (input.files[0].type == "application/pdf") {
//                console.log("Data Resum: ",input.files)
                    var htmlPreview =
                            '<img width="100" class="img-box" src="https://uwm.edu/freshwater/wp-content/uploads/sites/48/2018/09/pdf-icon-transparent-7.png" /><p class="text-center">' + input.files[0].name + '</p>';
                    document.getElementsByName("formDynamic[" + res[0] + "][foto_confirm]")[0].value = "oke";
                    var descZone = $(input).parent()
                            .find(".dropzone-desc");
                    var wrapperZone = $(input).parent();
                    var previewZone = $(input)
                            .parent()
                            .parent()
                            .find(".preview-zone");
                    var boxZone = $(input)
                            .parent()
                            .parent()
                            .find(".preview-zone")
                            .find(".box")
                            .find(".box-body");
                    wrapperZone.removeClass("dragover");
                    previewZone.removeClass("hidden");
                    descZone.addClass("hidden2");
                    boxZone.empty();
                    boxZone.append(htmlPreview);
                } else {
                    var htmlPreview = '<p style="color: red" >Unsupported Format File</p>';
                    var Paragraf = $(input).parent().parent().parent().find(".dropzone-errortext");
                    Paragraf.append(htmlPreview);
                    document.getElementsByName("formDynamic[" + res[0] + "][foto_confirm]")[0].value = "nopdf";
                }
            } else {
                var htmlPreview = '<p style="color: red">File size exceeds 1 MB</p>';
                var Paragraf = $(input).parent().parent().parent().find(".dropzone-errortext");
                Paragraf.append(htmlPreview);
//                document.getElementById("uploadTicketError").innerHTML = "Unsupported Format File";
                document.getElementsByName("formDynamic[" + res[0] + "][foto_confirm]")[0].value = "filelarge";
            }

        };

        reader.readAsDataURL(input.files[0]);
    }
}
function readFileNonRepeat(input) {
    console.log(input.files)
    if (input.files && input.files[0]) {
        var reader = new FileReader();
      
        reader.onload = function (e) {
            var FileSize = input.files[0].size / 1024 / 1024;
            if (FileSize < 1) {
                if (input.files[0].type == "application/pdf") {
                    var htmlPreview =
                            '<img width="100" class="img-box" src="https://uwm.edu/freshwater/wp-content/uploads/sites/48/2018/09/pdf-icon-transparent-7.png" /><p class="text-center">' + input.files[0].name + '</p>';
                    var descZone = $(input).parent()
                            .find(".dropzone-desc");
                    var wrapperZone = $(input).parent();
                    var previewZone = $(input)
                            .parent()
                            .parent()
                            .find(".preview-zone");
                    var boxZone = $(input)
                            .parent()
                            .parent()
                            .find(".preview-zone")
                            .find(".box")
                            .find(".box-body");
                    document.getElementsByName("fotoconfirm")[0].value = "oke";
                    document.getElementById("uploadTicketError").innerHTML = "";
                    wrapperZone.removeClass("dragover");
                    previewZone.removeClass("hidden");
                    descZone.addClass("hidden2");
                    boxZone.empty();
                    boxZone.append(htmlPreview);
                } else {
                    console.log("upload ticket: ", document.getElementById("uploadTicketError"));
                    document.getElementById("uploadTicketError").innerHTML = "Unsupported Format File";
                    document.getElementsByName("fotoconfirm")[0].value = "nopdf";
                }
            } else {
                console.log("upload ticket: ", document.getElementById("uploadTicketError"));
                document.getElementById("uploadTicketError").innerHTML = "File size exceeds 1 MB";
                document.getElementsByName("fotoconfirm")[0].value = "filelarge";
            }
        };

        reader.readAsDataURL(input.files[0]);
    }
}
function readFileNonRepeatIMG(input) {
    console.log(input.files)
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var FileSize = input.files[0].size / 1024 / 1024;
            if (FileSize < 1) {
                if (input.files[0].type == "image/jpeg" ||input.files[0].type == "image/png" ) {
                    var htmlPreview =
                            '<img width="100" class="img-box" src="' +
                            e.target.result +
                            '" />' +
                            '<p class="text-center">' + input.files[0].name + '</p>';
//                    var htmlPreview =
//                            '<img width="100" class="img-box" src="https://uwm.edu/freshwater/wp-content/uploads/sites/48/2018/09/pdf-icon-transparent-7.png" /><p class="text-center">' + input.files[0].name + '</p>';
                    var descZone = $(input).parent()
                            .find(".dropzone-desc");
                    var wrapperZone = $(input).parent();
                    var previewZone = $(input)
                            .parent()
                            .parent()
                            .find(".preview-zone");
                    var boxZone = $(input)
                            .parent()
                            .parent()
                            .find(".preview-zone")
                            .find(".box")
                            .find(".box-body");
                    document.getElementsByName("fotoconfirm")[0].value = "oke";
                    document.getElementById("uploadTicketError").innerHTML = "";
                    wrapperZone.removeClass("dragover");
                    previewZone.removeClass("hidden");
                    descZone.addClass("hidden2");
                    boxZone.empty();
                    boxZone.append(htmlPreview);
                } else {
                    console.log("upload ticket: ", document.getElementById("uploadTicketError"));
                    document.getElementById("uploadTicketError").innerHTML = "Unsupported Format File";
                    document.getElementsByName("fotoconfirm")[0].value = "notSupport";
                }
            } else {
                console.log("upload ticket: ", document.getElementById("uploadTicketError"));
                document.getElementById("uploadTicketError").innerHTML = "File size exceeds 1 MB";
                document.getElementsByName("fotoconfirm")[0].value = "filelarge";
            }
            var htmlPreview =
                    '<img width="100" class="img-box" src="' +
                    e.target.result +
                    '" />' +
                    "<p>";
//                    +
//                    input.files[0].name +
//                    "</p>";
            var descZone = $(input).parent()
                    .find(".dropzone-desc");
            var wrapperZone = $(input).parent();
            var previewZone = $(input)
                    .parent()
                    .parent()
                    .find(".preview-zone");
            var boxZone = $(input)
                    .parent()
                    .parent()
                    .find()(".preview-zone")
                    .find(".preview-zone")
                    .find(".box")
                    .find(".box-body");
          
            wrapperZone.removeClass("dragover");
            previewZone.removeClass("hidden");
            descZone.addClass("hidden2");
            boxZone.empty();
            boxZone.append(htmlPreview);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

function reset(e) {
    e.wrap("<form>")
            .closest("form")
            .get(0)
            .reset();
    e.unwrap();
}

$(".dropzone").change(function () {
    readFile(this);
});

function resetbtn(input) {
    alert("reset")
//   var dropzoneinput = $(input)
//            .parents(".form-group")
//            .find(".fotoconfirm");
//    console.log(dropzoneinput[0].name)
//    document.getElementsByName(dropzoneinput[0].name)[0].value = "nonono";
//    var descZone = $(input)
//            .parents(".form-group")
//            .find(".dropzone-desc");
//    console.log(descZone);
//    var boxZone = $(input)
//            .parents(".preview-zone")
//            .find(".box-body");
//    var previewZone = $(input).parents(".preview-zone");
//    var dropzone = $(input)
//            .parents(".form-group")
//            .find(".dropzone");
//    descZone.removeClass("hidden2");
//    boxZone.empty();
//    previewZone.addClass("hidden");
//    reset(dropzone);
}

//$(".dropzone").change(function () {
//    readFile(this);
//});

$(".dropzone-wrapper").on("dragover", function (e) {
    e.preventDefault();
    e.stopPropagation();
    $(this).addClass("dragover");
});

//$(".dropzone-wrapper").on("dragleave", function(e) {
//  e.preventDefault();
//  e.stopPropagation();
//  $(this).removeClass("dragover");
//});
$(".dropzone-wrapper").on("dragleave", function (e) {

    e.preventDefault();
    e.stopPropagation();
    $(this).removeClass("dragover");
});

$(".remove-preview").on("click", function () {
    try {
        var dropzoneinput = $(this)
                .parents(".form-group")
                .find(".fotoconfirm");
        document.getElementsByName(dropzoneinput[0].name)[0].value = "false";


    } catch (e) {
        document.getElementsByName("fotoconfirm")[0].value = "false";
    }
    var descZone = $(this)
            .parents(".form-group")
            .find(".dropzone-desc");
    console.log(descZone);
    var boxZone = $(this)
            .parents(".preview-zone")
            .find(".box-body");
    var previewZone = $(this).parents(".preview-zone");
    var dropzone = $(this)
            .parents(".form-group")
            .find(".dropzone");
    descZone.removeClass("hidden2");
    console.log(boxZone)
    boxZone.empty();
    previewZone.addClass("hidden");
    reset(dropzone);
});
