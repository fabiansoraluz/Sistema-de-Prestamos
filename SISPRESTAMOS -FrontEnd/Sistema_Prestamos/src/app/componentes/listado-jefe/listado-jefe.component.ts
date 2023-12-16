import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { CrudjefesService } from 'src/app/services/crudjefes.service';
import { TokenService } from 'src/app/services/token.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listado-jefe',
  templateUrl: './listado-jefe.component.html',
  styleUrls: ['./listado-jefe.component.css']
})
export class ListadoJefeComponent {

  listaJefe:any[]=[]
  usuario_rol:string;
  public page!:number


  constructor(
    private serviceJefe:CrudjefesService,
    private SToken:TokenService, 
    private router:Router){
    this.listarGrupos();
    this.usuario_rol = this.SToken.getAuthorities()[0];
  }

  listarGrupos(){
    this.serviceJefe.getListaGrupos().subscribe((data)=>{
      this.listaJefe = data;
    })
  }
  actualizar(idJefe:number){
    this.router.navigate(["/admin/mantenimiento-jefe/actualizar/",idJefe])
    console.log(idJefe)
  }

  eliminar(id:number){
    Swal.fire({
      title: 'Eliminado',
      text: "¿Seguro que deseas eliminar?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) { 
        this.serviceJefe.eliminar(id).subscribe(
          response =>{
            Swal.fire("Jefe Eliminado",response,"success")
            this.listarGrupos();
          },
          err=>{
            Swal.fire("Error de Eliminación",err.error.mensaje,"error")
          }
        )
      }
    })
  }
}
