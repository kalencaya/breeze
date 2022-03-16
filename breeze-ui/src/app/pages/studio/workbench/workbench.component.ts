import { AfterViewInit, Component, ElementRef, Injector, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { SplitterOrientation } from 'ng-devui';
import { Graph, Addon, Cell } from '@antv/x6';
import { WORKBENCH_CONFIG } from 'src/config/workbench-config';
import { WORKBENCH_MENU } from 'src/app/@core/data/studio.data';
import { X6graphService } from 'src/app/@core/services/x6graph.service';
import '@antv/x6-angular-shape';
import { BaseNodeComponent } from './base-node/base-node.component';
import { Node } from '@antv/x6';
@Component({
  selector: 'app-workbench',
  templateUrl: './workbench.component.html',
  styleUrls: ['./workbench.component.scss'],
})
export class WorkbenchComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('mainContainer') mainContainer: ElementRef;
  @ViewChild('menuContainer') menuContainer: ElementRef;
  graph: Graph;
  dnd: Addon.Dnd;
  currentCell: Cell;
  orientation: SplitterOrientation = 'horizontal';
  size = '12%';
  minSize = '12%';
  maxSize = '12%';
  menu = WORKBENCH_MENU;
  zoomOptions = [
    { label: '200%', value: 2 },
    { label: '150%', value: 1.5 },
    { label: '100%', value: 1 },
    { label: '75%', value: 0.75 },
    { label: '50%', value: 0.5 },
  ];
  zoomOptionSize = { label: '100%', value: 1 };
  constructor(private graphService: X6graphService, private injector: Injector) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.initGraph();
  }

  ngOnDestroy(): void {
    this.graph.dispose();
  }

  initGraph(): void {
    //init x6
    this.graph = new Graph({
      ...WORKBENCH_CONFIG,
      container: this.mainContainer.nativeElement,
      connecting: {
        snap: true,
        allowBlank: false,
        allowMulti: false,
        allowLoop: false,
        allowNode: false,
        allowEdge: false,
        connector: 'smooth',
        validateConnection({
          edge,
          edgeView,
          sourceView,
          targetView,
          sourcePort,
          targetPort,
          sourceMagnet,
          targetMagnet,
          sourceCell,
          targetCell,
          type,
        }) {
          if (sourcePort === 'outPort' && targetPort === 'inPort') {
            return true;
          } else {
            return false;
          }
        },
      },
    });
    //bind keyboard
    this.graph
      .bindKey('ctrl+c', () => {
        const cells = this.graph.getSelectedCells();
        if (cells.length) {
          this.graph.copy(cells);
        }
        return false;
      })
      .bindKey('ctrl+v', () => {
        if (!this.graph.isClipboardEmpty()) {
          const cells = this.graph.paste({ offset: 50 });
          this.graph.cleanSelection();
          this.graph.select(cells);
        }
        return false;
      })
      .bindKey('ctrl+z', () => {
        if (this.graph.history.canUndo()) {
          this.graph.history.undo();
        }
      })
      .bindKey('ctrl+y', () => {
        if (this.graph.history.canRedo()) {
          this.graph.history.redo();
        }
      })
      .bindKey('delete', () => {
        this.deleteCell();
      });
    //event listener todo
    this.graph
      .on('cell:contextmenu', ({ e, x, y, cell, view }) => {
        this.currentCell = cell;
        this.menuContainer.nativeElement.style.top = e.pageY + 'px';
        this.menuContainer.nativeElement.style.left = e.pageX - 240 + 'px';
        this.menuContainer.nativeElement.style.display = 'flex';
      })
      .on('node:dblclick', ({ e, x, y, cell, view }) => {
        this.currentCell = cell;
        console.log('双击弹出详情编辑框');
      })
      .on('blank:click', () => {
        this.menuContainer.nativeElement.style.display = 'none';
      })
      .on('cell:click', ({ e, x, y, cell, view }) => {
        this.currentCell = cell;
        this.menuContainer.nativeElement.style.display = 'none';
      });
    // drag and drop
    this.dnd = new Addon.Dnd({
      target: this.graph,
    });
    //注册angular节点
    this.registerAngularNode();
    this.graph.centerContent();
  }

  registerAngularNode(): void {
    Graph.registerAngularContent('base-node', { injector: this.injector, content: BaseNodeComponent });
  }

  dragNode(e: MouseEvent): void {
    const target = e.currentTarget as HTMLElement;
    const menuType = target.getAttribute('menuType');
    const menuName = target.getAttribute('menuName');
    const node: Node<Node.Properties> = this.createNode(target.innerText, menuName, menuType);
    this.dnd.start(node, e);
  }

  createNode(title: string, name: string, type: string): Node<Node.Properties> {
    const node = this.graph.createNode({
      data: {
        ngArguments: {
          title: title,
          name: name,
        },
      },
      width: 180,
      height: 32,
      shape: 'angular-shape',
      componentName: 'base-node',
      ports: {
        groups: {
          in: {
            position: 'top',
            attrs: {
              circle: {
                r: 4,
                magnet: true,
                stroke: '#31d0c6',
                strokeWidth: 2,
                fill: '#fff',
              },
            },
          },
          out: {
            position: 'bottom',
            attrs: {
              circle: {
                r: 4,
                magnet: true,
                stroke: '#31d0c6',
                strokeWidth: 2,
                fill: '#fff',
              },
            },
          },
        },
      },
    });
    //判断组件类型，控制节点的连接桩位置
    if (type === 'source') {
      node.addPort({
        id: 'outPort',
        group: 'out',
      });
    } else if (type === 'trans') {
      node.addPorts([
        {
          id: 'inPort',
          group: 'in',
        },
        {
          id: 'outPort',
          group: 'out',
        },
      ]);
    } else if (type === 'sink') {
      node.addPort({
        id: 'inPort',
        group: 'in',
      });
    }
    return node;
  }

  createEdge(){
    
  }
  editNode() {
    console.log(this.currentCell);
    this.menuContainer.nativeElement.style.display = 'none';
  }
  deleteCell() {
    const cells = this.graph.getSelectedCells();
    if (cells.length) {
      this.graph.removeCells(cells);
    }
    this.menuContainer.nativeElement.style.display = 'none';
  }

  saveGraph(): void {
    const data = this.graph.toJSON();
    console.log(data, `画布的结果转化为JSON`);
  }
  zoomTo() {
    this.graph.zoomTo(this.zoomOptionSize.value);
  }

  onSearch(term) {
    console.log(term);
  }
}
