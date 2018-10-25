/**
 * BetonQuest - advanced quests for Bukkit
 * Copyright (C) 2016  Jakub "Co0sh" Sapalski
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

import pl.betoncraft.betonquest.BetonQuest;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.InstructionParseException;
import pl.betoncraft.betonquest.QuestRuntimeException;
import pl.betoncraft.betonquest.VariableNumber;
import pl.betoncraft.betonquest.api.QuestEvent;
import pl.betoncraft.betonquest.metadata.DefaultTypeMetadata;
import pl.betoncraft.betonquest.metadata.TypeMetadata;
import pl.betoncraft.betonquest.metadata.datatype.NumberData;
import pl.betoncraft.betonquest.metadata.format.Argument;
import pl.betoncraft.betonquest.metadata.format.ArgumentFormat;
import pl.betoncraft.betonquest.metadata.text.StringText;
import pl.betoncraft.betonquest.utils.PlayerConverter;

/**
 * Damages the player
 *
 * @author Jakub Sapalski
 */
public class DamageEvent extends QuestEvent {

	private final VariableNumber damage;

	public DamageEvent(Instruction instruction) throws InstructionParseException {
		super(instruction);
		damage = instruction.getVarNum();
	}

	@Override
	public void run(String playerID) throws QuestRuntimeException {
		PlayerConverter.getPlayer(playerID).damage(Math.abs(damage.getDouble(playerID)));
	}

	public static TypeMetadata getMetadata() {
		return new DefaultTypeMetadata()
			.setName(new StringText("Damage"))
			.setDescription(new StringText("Damages the player"))
			.setPlugin(BetonQuest.getInstance())
			.setFormat(new ArgumentFormat()
				.require(new Argument()
					.setName(new StringText("Damage"))
					.setDescription(new StringText("Amount of damage to deal to the player"))
					.setData(new NumberData<Integer>(Integer.class).setMin(0).allowVariable())));
	}

}
