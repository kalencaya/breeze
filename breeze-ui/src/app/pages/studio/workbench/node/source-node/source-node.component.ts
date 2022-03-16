import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-source-node',
  templateUrl: './source-node.component.html',
  styleUrls: ['./source-node.component.scss'],
})
export class SourceNodeComponent implements OnInit {
  @Input() title: string;
  constructor() {}

  ngOnInit(): void {}
}
