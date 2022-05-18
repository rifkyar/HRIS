$("#VaccineDate").datepicker({
    maxDate: 0
});
$('#fileVaccine').on('change',function(){
    for(var i=0; i< $(this).get(0).files.length; ++i){
      var file1 = $(this).get(0).files[i].size;
      if(file1){
        var file_size = $(this).get(0).files[i].size;
        if(file_size > 1000000){
          $('#error-message').html("File upload size is larger than 1MB");
          $('#error-message').css("display","block");
          $('#error-message').css("color","red");
          $('#error-message').val("gagal");
          var coba = $('#error-message').val();
          console.log(coba);
        }else{
          $('#error-message').css("display","none");
          $('#error-message').val("ada isi");
        }
      }
    }
  });
$('#formVaccine').on('submit',function(event){
    event.preventDefault();
    var form = this;
    var VaccType = $('#Type').val();
    var VaccineDate = $('#VaccineDate').val();
    var VaccLocation = $('#VaccineLocation').val();
    var DosesType = $('#DosesType').val();
    var alertFile = $('#error-message').val();
    console.log (VaccLocation);
    if(VaccType === "" || VaccineDate === "" || VaccineLocation === "" || DosesType === ""||alertFile === "gagal"||alertFile === ""){
        console.log("coba kosongan");
        Swal.fire({
            type: 'warning',
            title: 'Sorry...',
            showConfirmButton: true,
            text: "Please fill all text field and Check your Uploaded File"
        });
    }else if(VaccLocation.lenght < 3){
        console.log("coba lokasi");
        Swal.fire({
            type: 'warning',
            title: 'Sorry...',
            showConfirmButton: true,
            text: "Please Insert Location More Than 3 Character"
        });
    }else{
        console.log("oke");
        Swal.fire({
            title: 'Are you sure?',
            text: "You will save this Training data",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, saved!' 
        }).then((result) =>{
            if (result.value) {
                Swal.fire({
                title: 'Saved!',
                type: 'success',
                text: 'Your Vaccination data has been saved!',
                showConfirmButton: false,
                timer: 50000
                });
                form.submit();
                }
        });
    }
});