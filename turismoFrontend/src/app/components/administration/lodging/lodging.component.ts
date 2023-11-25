import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BASE_BACKEND_URL } from '../../../enviroment';
import { LodgingModalComponent } from '../lodging-modal/lodging-modal.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ObtainHeader } from '../../../helpers/obtainHeader';
import { MatTableModule } from '@angular/material/table';
import { Observable } from 'rxjs';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

@Component({
  selector: 'app-lodging',
  standalone: true,
  imports: [CommonModule, MatTableModule, HttpClientModule, MatDialogModule],
  templateUrl: './lodging.component.html',
  styleUrl: './lodging.component.css'
})
export class LodgingComponent implements OnInit{
  constructor(private http: HttpClient, private dialog: MatDialog, private obtainHeader : ObtainHeader) {}


 loadLodgings(): Observable<any[]> {
    const headers = this.obtainHeader.getAuthHeader();

    return this.http
      .get<any[]>(`${BASE_BACKEND_URL}/api/lodging`, { headers })
  
  }

  ngOnInit() {
   this.loadLodgings().subscribe(
    (response) => {
      console.log(response);
      this.dataSource=response;
    },
    (error) => {
      console.log(error);
    }
   )
  }



  displayedColumns: string[] = [ 'id', 'name', 'tipo de alojamiento','habitaciones','check in','check out','ciudad'];
  dataSource: any[] = [];
  selectedRow: any | null = null;

onRowClicked(row: any) {
  this.selectedRow = row;
  
}

selectedData: any | null = null;
isUpdate: boolean = false;

getUniqueLodgings(touristDestinations: any[]): string[] {
  const uniqueLodgings = new Set<string>();
  touristDestinations.forEach(destination => {
    if (destination.city && destination.city.lodgings) {
      destination.city.lodgings.forEach((lodging:any) => {
        uniqueLodgings.add(lodging.name);
      });
    }
  });
  return Array.from(uniqueLodgings);
}

openModal() {
  const dialogRef = this.dialog.open(LodgingModalComponent, {
    width: '800px',
    data: { selectedData: this.selectedData}
  });

  dialogRef.componentInstance.closeModal.subscribe(() => {
    dialogRef.close(); // Cierra el modal interno desde el padre
  });
  dialogRef.componentInstance.submitData.subscribe((data) => {
    this.onSubmitData(data); // Función para manejar los datos enviados desde el modal
    dialogRef.close(); // Cierra el modal
  });
}


downloadPDF() {
  const table = document.querySelector('.table'); // Selecciona tu tabla

  if (table instanceof HTMLElement) {
    html2canvas(table).then((canvas) => {
      const imgData = canvas.toDataURL('image/png');
    const pdf = new jsPDF();
    const imgProps = pdf.getImageProperties(imgData);
    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
    
    pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
    pdf.save('Hospedajes.pdf');
    });
  } else {
    console.error('No se encontró la tabla');
  }
}


onAdd(){
  this.selectedData = null;
  this.isUpdate = false;
this.openModal()
}

onUpdate(){

  if (this.selectedRow) {
    this.selectedData = { ...this.selectedRow }; // Guarda la información de la fila seleccionada
    this.isUpdate = true; // Establece la bandera de actualización a true
  }

  this.openModal()
}
onDelete(id:number | undefined){
  if(id){
    const headers = this.obtainHeader.getAuthHeader();
    this.http
    .delete<any>(`${BASE_BACKEND_URL}/api/lodging/${id}`,{ headers }).subscribe((res)=>{
      console.log(res);
      this.loadLodgings().subscribe((response)=>{
       this.dataSource=response;
      }, (error)=>{
        console.log("error " , error);
      });
    },
    (error) => {
      console.error('deleting error:', error);
      // Manejar el error si es necesario
    })
  }

}


onSubmitData(data: any):void {
  const headers = this.obtainHeader.getAuthHeader();

  if (this.isUpdate && this.selectedRow) {
    console.log(this.selectedRow);
    this.http
      .put<any>(`${BASE_BACKEND_URL}/api/lodging/${this.selectedRow.lodging.id}`, data,{ headers }).subscribe((res)=>{
        console.log(res);
        this.loadLodgings().subscribe((response)=>{ this.dataSource=response;});
      },
      (error) => {
        console.error('update error:', error);
        // Manejar el error si es necesario
      })

  } else {
    console.log(data, this.isUpdate);
    console.log(headers);
    this.http
      .post<any>(`${BASE_BACKEND_URL}/api/lodgingadmin`, data,{ headers }).subscribe((res)=>{
        console.log(res);
        this.loadLodgings().subscribe((response)=>{ this.dataSource=response;});
      },
      (error) => {
        console.error('Create error:', error);
        // Manejar el error si es necesario
      })
  }
}
}
