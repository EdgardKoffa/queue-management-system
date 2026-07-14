package com.nsglobal.queue.display.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisplayDevice {

	private String deviceId;

    private DisplayDeviceType type;

    private Long branchId;

    private Long counterId;

}
