(function(){
    const eyes = document.querySelectorAll(".form__icon--eye");
    eyes.forEach(eye=>{
        eye.addEventListener("click",()=>{
            const input = eye.previousElementSibling;
            if(input.type=="password"){
                input.type="text"
                eye.setAttribute("src","./img/eye-close.svg")
            }else{
                input.type="password"
                eye.setAttribute("src","./img/eye.svg")
            }
        })
    })
})()