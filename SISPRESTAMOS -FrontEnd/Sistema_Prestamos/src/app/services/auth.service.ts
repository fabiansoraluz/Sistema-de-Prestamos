import { HttpClient } from '@angular/common/http';
import { Host, Injectable } from '@angular/core';
import { LoginUsuario } from '../modelos/LoginUsuario';
import { Observable } from 'rxjs';
import { JwtDto } from '../modelos/jwt-dto';
import { Usuario } from '../modelos/Usuario';
import { RecuperarUsuario } from '../modelos/RecuperarUsuario';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  host:string="http://localhost:8091/api/"

  constructor(private http:HttpClient) { }

  public login(user:LoginUsuario):Observable<any>{
    return this.http.post<JwtDto>(this.host+"login",user)
  }
  public registrar(user:Usuario):Observable<any>{
    return this.http.post<Usuario>(this.host+"registrar",user)
  }
  public recuperar(user:RecuperarUsuario):Observable<any>{
    return this.http.post<string>(this.host+"recuperar",user)
  }
  public enviarCorreo(correo:string):Observable<any>{
    return this.http.post<string>(this.host+"mail/"+correo,null)
  }
  public buscarUsuarioXId(id:number):Observable<any>{
    return this.http.get<Usuario>(this.host+"usuario/"+id)
  }
}
