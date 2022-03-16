import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-trans-node',
  templateUrl: './trans-node.component.html',
  styleUrls: ['./trans-node.component.scss'],
})
export class TransNodeComponent implements OnInit {
  @Input() title: string;
  constructor() {}

  ngOnInit(): void {}
}
