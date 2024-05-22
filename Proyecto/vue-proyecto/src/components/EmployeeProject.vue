<template>
    <v-container fluid>
        <v-row class="seleccionable justify-left">
            <!-- <v-spacer></v-spacer> -->
            <v-col>
                <v-select chips label="Elige un proyecto" :items="projects" 
                item-text="descripcion" item-value="idProyecto" variant="outlined">
                </v-select>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <div style="margin: 0px 200px;">
                    <v-data-table :headers="headers" :items="employees" class="elevation-1" :item-per-page="5">
                        <template v-slot:item.actions="{ item }">
                            <v-simple-checkbox v-model="item.actions"></v-simple-checkbox>
                        </template>
                    </v-data-table>
                </div>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <div class="text-center">
                        <v-btn class="ma-2" color="primary">
                            Aceptar
                            <v-icon color="white" icon="mdi-check-circle" size="small"></v-icon>
                        </v-btn>
                    </div>
            </v-col>
        </v-row>
    </v-container>
</template>


<script>
import axios from 'axios'
export default {
    data() {
        return {
            headers: [
                { text: 'Nombre', value: 'nombre' },
                { text: 'Primer Apellido', value: 'primerApellido' },
                { text: 'Segundo Apellido', value: 'segundoApellido' },
                { text: 'Asignado', value: 'actions' },
            ],
            projects: [],
            employees: [],
            editedIndex: -1,
    editedItem: {
        nombre: '',
        primerApellido: '',
        segundoApellido: ''
    },
    defaultItem: {
      nombre: '',
      primerApellido: '',
      segundoApellido: ''
    },
        }
    },

    mounted() {
        this.getEmployees(),
        this.getProjects()
    },

    methods: {
        async getEmployees() {
      const URL = `http://localhost:8080/empleados`

      await axios.get(URL)
        .then(response => {
          this.employees = response.data
        })
        .catch((error) => {
          console.error("Error recuperando los datos: " + error) //hacer console.log
        })
    },
    async getProjects() {
        try {
                const response = await axios.get('http://localhost:8080/projects');
                this.projects = response.data.map(project => ({
                    idProject: project.idProject,
                    descripcion: project.descripcion
                }));
            } catch (error) {
                console.error('Error:', error);
            }
    },



    }
    
    
}
</script>

