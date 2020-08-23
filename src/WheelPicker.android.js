/**
 * @prettier
 * @flow
 * */

import React from 'react'
import { requireNativeComponent, View } from 'react-native'

const WheelPickerView = requireNativeComponent('WheelPicker', null)

type Props = {
  data: Array<string>,
  isCyclic?: boolean,
  selectedItemTextColor?: string,
  selectedItemTextSize?: number,
  indicatorWidth?: number,
  hideIndicator?: boolean,
  indicatorColor?: string,
  itemTextColor?: string,
  itemTextSize?: number,
  selectedItem?: number,
  backgroundColor?: string,
  onItemSelected?: number => void,
  disabled?: boolean,
}

export default class WheelPicker extends React.Component<Props> {
  static defaultProps = {
    style: {
      width: 180,
      height: 150,
    },
  }

  constructor(props) {
    super(props);
    this.state = {
      pickerKey:1
    }
  }

  componentDidUpdate(prevProps) {
    if(!this.props.data || !prevProps.data) return;
    if(this.props.data.length != prevProps.data.length || this.props.initPosition != prevProps.initPosition) {
      this.setState({pickerKey:this.state.pickerKey+1});
    }
  }

  onItemSelected = (event: any) => {
    if (this.props.onItemSelected) {
      this.props.onItemSelected(event.nativeEvent.position)
    }
  }

  render() {
    const { isCyclic, data } = this.props
    return (
        <WheelPickerView
          {...this.props}
          isCyclic={data.length > 2 ? isCyclic : false}
          onChange={this.onItemSelected}
        />
    )
  }
}
