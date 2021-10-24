import { AfterViewInit, Component, OnInit } from '@angular/core';
declare var jsPlumb: any;

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.scss'],
})
export class JobComponent implements OnInit, AfterViewInit {
  jsPlumbInstance;
  showConnectionToggle = false;
  constructor() {}
  ngAfterViewInit(): void {
    this.jsPlumbInstance = jsPlumb.getInstance();
  }

  showConnectOnClick() {
    this.showConnectionToggle = !this.showConnectionToggle;
    if (this.showConnectionToggle) {
      this.jsPlumbInstance = jsPlumb.getInstance();
      this.connectSourceToTargetUsingJSPlumb();
    } else {
      this.jsPlumbInstance.reset();
    }
  }
  ngOnInit(): void {}

  connectSourceToTargetUsingJSPlumb() {
    let labelName;
    labelName = 'connection';
    this.jsPlumbInstance.connect({
      connector: ['Flowchart', { stub: [212, 67], cornerRadius: 1, alwaysRespectStubs: true }],
      source: 'Source',
      target: 'Target1',
      anchor: ['Right', 'Left'],
      paintStyle: { stroke: '#456', strokeWidth: 4 },
      overlays: [['Label', { label: labelName, location: 0.5, cssClass: 'connectingConnectorLabel' }]],
    });
  }
}
