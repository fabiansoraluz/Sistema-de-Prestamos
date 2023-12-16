import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilesService {

  constructor(){}

  password_event():void{
    const eyes = document.querySelectorAll(".form__icon--eye");
    eyes.forEach(eye=>{
        eye.addEventListener("click",()=>{
          const formItem = eye.closest('.form__item'); // Encuentra el elemento .form__item más cercano
          const input = formItem.querySelector('.form__input') as HTMLInputElement;
          if(input.type=="password"){
              input.type="text"
              eye.setAttribute("src","../../../assets/img/eye-close.svg")
          }else{
              input.type="password"
              eye.setAttribute("src","../../../assets/img/eye.svg")
          }
        })
    })
  }

}
