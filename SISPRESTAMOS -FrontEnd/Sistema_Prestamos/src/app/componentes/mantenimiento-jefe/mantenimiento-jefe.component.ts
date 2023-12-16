import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/modelos/Usuario';
import { Ubigeo } from 'src/app/modelos/Ubigeo';
import { CrudjefesService } from 'src/app/services/crudjefes.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { JefeDTO } from 'src/app/modelos/JefeDTO';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-mantenimiento-jefe',
  templateUrl: './mantenimiento-jefe.component.html',
  styleUrls: ['./mantenimiento-jefe.component.css']
})

export class MantenimientoJefeComponent implements OnInit {

  JefeObj: JefeDTO = new JefeDTO()
  formulario:FormGroup
  isUpdate: boolean = false
  listUbigeo: Ubigeo[] = []

  constructor(
    private jefeService: CrudjefesService,
    private router: Router,
    private route: ActivatedRoute,
    private FB:FormBuilder
  ) {
  }
  ngOnInit(): void {

    // Listar Ubigeos
    this.jefeService.getUbigeo().subscribe(x => this.listUbigeo = x)

    // Construimos el formulario
    this.formulario = this.FB.group({
      username:['',
        [
          Validators.required,
          Validators.pattern("(?!\\s+)(?=.*[a-z])[a-zA-ZáéíóúÁÉÍÓÚñ.\\d]{6,12}")
        ]
      ],
      correo:['',
        [
          Validators.required,
          Validators.email,
          Validators.pattern("(?![0-9])[a-zA-ZáéíóúüÁÉÍÓÚÜñ\\d]+[@][a-zA-ZáéíóúüÁÉÍÓÚÜñ]+[.][a-zA-Z]{2,3}")
        ]
      ],
      nombre:['',
        [
          Validators.required,
          Validators.pattern("(?!\\s+)[a-zA-ZáéíóúüÁÉÍÓÚÜñ\\s]{4,30}")
        ]
      ],
      paterno:['',
        [
          Validators.required,
          Validators.pattern("(?!\\s+)[a-zA-ZáéíóúüÁÉÍÓÚÜñ]{3,30}")
        ]
      ],
      materno:['',
        [
          Validators.required,
          Validators.pattern("(?!\\s+)[a-zA-ZáéíóúüÁÉÍÓÚÜñ]{3,30}")
        ]
      ],
      celular:['',
        [
          Validators.required,
          Validators.pattern("(?![0])[\\d]{9}")
        ]
      ],
      dni:['',
        [
          Validators.required,
          Validators.pattern("[\\d]{8}"),
          Validators.min(5000)
        ]
      ],
      ubigeo:['',
        [
          Validators.required,
          Validators.pattern("[\\d]{6}")
        ]
      ],
      ubigeoGrupo:['',
        [
          Validators.required,
          Validators.pattern("[\\d]{6}")
        ]
      ]
    })

    // En caso de tener idPersona Setteamos valores
    const idPersona: number = this.route.snapshot.params['actualizar']
    if (idPersona) {
      this.isUpdate = true
      this.jefeService.obtenerIdJefePorPersona(idPersona).subscribe(
        response => {
          this.jefeService.buscarJefePorId(response).subscribe(
            x => {
              this.JefeObj=x
              this.formulario.patchValue({
                username:x.usuario.username,
                correo:x.usuario.correo,
                nombre:x.usuario.persona.nombre,
                paterno:x.usuario.persona.paterno,
                materno:x.usuario.persona.materno,
                celular:x.usuario.persona.celular,
                dni:x.usuario.persona.dni,
                ubigeo:x.usuario.persona.ubigeo.id,
                grupo:x.grupo.id,
                ubigeoGrupo:x.grupo.ubigeo.id
              })
            }
          )
        }
      )
    }
  }

  btnGrabar() {
    //Construimos al Jefe Prestamista
    this.buildUser()
    if (this.isUpdate) {
      this.actualizar();
    } else {
      //Setteamos el password
      this.JefeObj.usuario.password = "test";
      this.registrar();
    }
  }
  registrar(){
    this.jefeService.registrarJefe(this.JefeObj).subscribe(
      response => {
        this.router.navigate(['/admin/listado-jefe']);
        Swal.fire("Registro Exitoso", "El jefe prestamista fue registrado.", "success");
      },
      err => {
        Swal.fire("Error de registro", err.error.mensaje, "error" );
      }
    );
  }
  actualizar(){
    this.jefeService.actualizarJefe(this.JefeObj).subscribe(
      response => {
        this.router.navigate(['/admin/listado-jefe']);
        Swal.fire("Actualización Exitosa", "El jefe prestamista " + response.usuario.persona.nombre + " fue actualizado.", "success");
      },
      err => {
        Swal.fire("Error de Actualización", err.error.mensaje, "error")
      }
    );
  }
  buildUser(){
    
    this.JefeObj.usuario.username           = this.formulario.get("username").value
    this.JefeObj.usuario.correo             = this.formulario.get("correo").value
    this.JefeObj.usuario.persona.nombre     = this.formulario.get("nombre").value
    this.JefeObj.usuario.persona.paterno    = this.formulario.get("paterno").value
    this.JefeObj.usuario.persona.materno    = this.formulario.get("materno").value
    this.JefeObj.usuario.persona.celular    = this.formulario.get("celular").value
    this.JefeObj.usuario.persona.dni        = this.formulario.get("dni").value
    this.JefeObj.usuario.persona.ubigeo.id  = this.formulario.get("ubigeo").value
    this.JefeObj.grupo.ubigeo.id               = this.formulario.get("ubigeoGrupo").value
  }
}
