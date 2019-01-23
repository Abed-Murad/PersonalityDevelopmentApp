var config = {
    apiKey: "AIzaSyB8lTeuzyjP88hQ8SKfONNBky27h7TMI1c",
    authDomain: "myfeed-dccdb.firebaseapp.com",
    databaseURL: "https://myfeed-dccdb.firebaseio.com",
    projectId: "myfeed-dccdb",
    storageBucket: "myfeed-dccdb.appspot.com",
    messagingSenderId: "107769501297"
};
firebase.initializeApp(config);
const firestore = firebase.firestore();
firestore.settings({
    timestampsInSnapshots: true
});

const colRef = firestore.collection("Posts");

const urlField = document.getElementById("urlField");
const imageUrlField = document.getElementById("imageUrlField");
const titleField = document.getElementById("titleField");
const bodyField = document.getElementById("bodyField");
const isVideoCheckBox = document.getElementById("isVideoCheckBox");
const saveButton = document.getElementById("saveButton");

saveButton.addEventListener("click", function () {

    console.log("clicked");


    let url = urlField.value;
    let imageUrl = imageUrlField.value;
    let title = titleField.value;
    let body = bodyField.value;
    let isVideoChecked = isVideoCheckBox.checked;
    let timestamp = firebase.firestore.FieldValue.serverTimestamp();
    let tags = ["tag1", "tag2", "tag2"];

    console.log(url);
    console.log(imageUrl);
    console.log(title);
    console.log(body);
    console.log(timestamp);
    console.log(isVideoChecked);
    console.log(tags);

    colRef.add({
        url: url,
        imageUrl: imageUrl,
        title: title,
        body: body,
        isVideo: isVideoChecked,
        date: timestamp,
        tags: tags

    }).then(function () {
        urlField.value = "";
        imageUrlField.value = "";
        titleField.value = "";
        bodyField.value  = "";
        isVideoCheckBox.checked = false;
        console.log("status Saved!");
    }).catch(function (error) {
        console.log("Got an error: " + error);
    })
});