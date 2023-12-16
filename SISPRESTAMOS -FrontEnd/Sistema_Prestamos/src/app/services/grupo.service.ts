import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GrupoService {

  private host = "http://localhost:8091/api/grupo";

  constructor(private http:HttpClient) { }

  public buscarIDXPrestamista(username:string):Observable<any>{
    return this.http.get<number>(this.host+"/prestamista/"+username);
  }

  public buscarIDXJefe(username:string):Observable<any>{
    return this.http.get<number>(this.host+"/jefe/"+username);
  }

  public listarGrupos():Observable<any>{
    return this.http.get<[number]>(this.host);
  }

}
