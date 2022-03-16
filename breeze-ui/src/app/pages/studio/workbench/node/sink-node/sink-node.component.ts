import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-sink-node',
  templateUrl: './sink-node.component.html',
  styleUrls: ['./sink-node.component.scss'],
})
export class SinkNodeComponent implements OnInit {
  @Input() title: string;
  constructor() {}

  ngOnInit(): void {}
}
