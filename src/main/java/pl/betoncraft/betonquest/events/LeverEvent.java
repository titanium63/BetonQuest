/**
 * BetonQuest - advanced quests for Bukkit
 * Copyright (C) 2015  Jakub "Co0sh" Sapalski
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pl.betoncraft.betonquest.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import pl.betoncraft.betonquest.InstructionParseException;
import pl.betoncraft.betonquest.api.QuestEvent;
import pl.betoncraft.betonquest.utils.Utils;

/**
 * This event turns on, of or toggles blocks like lever, door, trapdoor etc.
 * 
 * @author Jakub Sapalski
 */
public class LeverEvent extends QuestEvent {
	
	private Location loc;
	private ToggleType type;
	
	private enum ToggleType {
		ON, OFF, TOGGLE
	}

	public LeverEvent(String packName, String instructions) throws InstructionParseException {
		super(packName, instructions);
		String[] parts = instructions.split(" ");
		if (parts.length < 3) {
			throw new InstructionParseException("Not enough arguments");
		}
		loc = new Utils.LocationData(packName, parts[1]).getLocation();
		try {
			type = ToggleType.valueOf(parts[2].toUpperCase());
		} catch (IllegalArgumentException e) {
			type = ToggleType.TOGGLE;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run(String playerID) {
		Block block = loc.getBlock();
		if (block.getType() != Material.LEVER) {
			return;
		}
		switch (type) {
		case ON:
			block.setData((byte) (block.getData() | 0x8));
			break;
		case OFF:
			block.setData((byte) (block.getData() & ~0x8));
			break;
		case TOGGLE:
			block.setData((byte) (block.getData() ^ 0x8));
			break;
		}
	}

}