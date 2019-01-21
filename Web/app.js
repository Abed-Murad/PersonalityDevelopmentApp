  var config = {
    apiKey: "AIzaSyB8lTeuzyjP88hQ8SKfONNBky27h7TMI1c",
    authDomain: "myfeed-dccdb.firebaseapp.com",
    databaseURL: "https://myfeed-dccdb.firebaseio.com",
    projectId: "myfeed-dccdb",
    storageBucket: "myfeed-dccdb.appspot.com",
    messagingSenderId: "107769501297"
  };
  firebase.initializeApp(config);
  var firestore = firebase.firestore();

  const docRef = firestore.doc("sample/Posts");

  const saveButton = document.getElementById("saveButton");
  
  const urlField = document.getElementById("urlField");
  //const imageUrlField = document.getElementById("imageUrlField");
  const titleField = document.getElementById("titleField");
  const bodyField = document.getElementById("bodyField");
  const isVideoCheckBox = document.getElementById("isVideoCheckBox");

  saveButton.addEventListener("click" , function(){
    const url = urlField.value ; 
   // const imageUrlField = imageUrlField.value ; 
    const titleField = titleField.value ; 
    const bodyField = bodyField.value ; 

     // console.log(isVideoCheckBox.checked + "");
      console.log(url);
    //  console.log(imageUrlField);
      console.log(titleField);
      console.log(bodyField);
      console.log(url);

    docRef.set({
      not:imageUrlField


    }).then( function(){
      console.log("status Saved!");
    }).catch(function(error){
      console.log("Got an error: "+  error);
    })
  });