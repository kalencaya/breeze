import { Component, ElementRef, Input, OnInit } from '@angular/core';
import { DeptService } from 'src/app/@core/services/dept.service';

@Component({
  selector: 'app-dept-delete',
  templateUrl: './dept-delete.component.html',
  styleUrls: ['./dept-delete.component.scss'],
})
export class DeptDeleteComponent implements OnInit {
  parent: HTMLElement;
  @Input() data: any;
  constructor(private deptService: DeptService, private elr: ElementRef) {}

  ngOnInit(): void {
    this.parent = this.elr.nativeElement.parentElement;
  }

  delete() {
    console.log(this.data.items);
    this.deptService.delete(this.data.items).subscribe((d) => {
      if (d.success) {
        this.data.refresh();
      }
      this.data.onClose();
    });
  }
}
