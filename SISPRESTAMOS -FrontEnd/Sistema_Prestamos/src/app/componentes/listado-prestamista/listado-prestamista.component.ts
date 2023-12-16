import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, tap } from 'rxjs';
import { Persona } from 'src/app/modelos/Persona';
import { Prestamista } from 'src/app/modelos/Prestamista';
import { Usuario } from 'src/app/modelos/Usuario';
import { CrudprestamistasService } from 'src/app/services/crudprestamistas.service';
import { GrupoService } from 'src/app/services/grupo.service';
import { TokenService } from 'src/app/services/token.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listado-prestamista',
  templateUrl: './listado-prestamista.component.html',
  styleUrls: ['./listado-prestamista.component.css']
})
export class ListadoPrestamistaComponent  implements OnInit{

  public listaPrestamista:any[] = []
  public page:number
  public esModificacion: boolean = false;
  public usuario_rol:string;
  public grupo:number;
  public grupos:[number];

  prestamista:Prestamista = new Prestamista();
  
  constructor(
    private SGrupo:GrupoService,
    private prestamistaService:CrudprestamistasService,
    private router:Router,
    private SToken:TokenService){
    this.usuario_rol = this.SToken.getAuthorities()[0];
  }
  ngOnInit(): void {
    //Llenar combo de grupos
    this.SGrupo.listarGrupos().subscribe(
      response => {
        this.grupos=response
        if(response.length>0){
          if(this.usuario_rol === 'prestamista'){
            this.SGrupo.buscarIDXPrestamista(this.SToken.getUsername()).subscribe(
              response => {
                this.grupo=response
                this.obtenerPrestamistasPorGrupo(this.grupo);
              }
            )
          }
          else if(this.usuario_rol === 'jefe'){
            this.SGrupo.buscarIDXJefe(this.SToken.getUsername()).subscribe(
              response => {
                this.grupo=response
                this.obtenerPrestamistasPorGrupo(this.grupo);
              }
            )
          }
          else if(this.grupos.length == 1 || this.usuario_rol === 'admin' && this.grupos.length > 1){
            this.grupo=this.grupos[0];
            this.obtenerPrestamistasPorGrupo(this.grupo);
          }
        }else{
          this.grupo=0;
        }
      }
    )
  }

  obtenerPrestamistasPorGrupo(idGrupo: number): void {

    this.prestamistaService.obtenerPrestamistasPorGrupo(idGrupo)
      .subscribe((data) => {console.log(data)
        this.listaPrestamista = data;
      });
  }
  

  eliminarPrestamista(id: number) {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.prestamistaService.eliminarPrestamita(id).subscribe((response) => {
          this.obtenerPrestamistasPorGrupo(this.grupo);
          this.router.navigate(['/admin/lista-prestamista']);
        });
      }
    });
  }
  
  // Método para redireccionar al componente de mantenimiento al hacer clic en "Actualizar"
  redireccionarAMantenimiento(idPrestamista: number) {
    this.prestamistaService.buscarPrestamistaPorId(idPrestamista).subscribe(
      (prestamista: Prestamista) => {
        this.prestamista = prestamista;
        this.esModificacion = true;
        this.router.navigate(['/admin/mantenimiento-prestamista/actualizar', idPrestamista]);
      },
      (error) => {
        console.error('Error al buscar prestamista por ID:', error);
      }
    );
  }

  registrar(){
    if(this.grupos.length < 1){
      Swal.fire("Error del Sistema","Debes registrar al menos un Jefe Prestamista","error");
    }else{
      this.router.navigate(['/admin/mantenimiento-prestamista/registrar', this.grupo ]);
    }
  }

  //Método para cambiar grupo
  cambiarGrupo(){
    this.obtenerPrestamistasPorGrupo(this.grupo);
  }

}
