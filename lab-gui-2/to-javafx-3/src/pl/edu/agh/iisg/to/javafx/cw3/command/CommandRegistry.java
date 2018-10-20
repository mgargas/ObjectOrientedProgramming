package pl.edu.agh.iisg.to.javafx.cw3.command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandRegistry {

	private ObservableList<Command> commandStack = FXCollections
			.observableArrayList();
	private ObservableList<Command> undoCommandStack = FXCollections
			.observableArrayList();

	public void executeCommand(Command command) {
		command.execute();
		undoCommandStack.removeAll();
		commandStack.add(command);
	}

	public void redo() {
		if(!this.undoCommandStack.isEmpty()){
			Command lastUndoCommand = this.undoCommandStack.get(this.undoCommandStack.size() - 1);
			this.undoCommandStack.remove(this.undoCommandStack.size() - 1);
			lastUndoCommand.redo();
			this.commandStack.add(lastUndoCommand);
		}
	}

	public void undo() {
		if(!this.commandStack.isEmpty()){
			Command lastCommand = this.commandStack.get(this.commandStack.size() - 1);
			this.commandStack.remove(this.commandStack.size() - 1);
			lastCommand.undo();
			undoCommandStack.add(lastCommand);
		}
	}

	public ObservableList<Command> getCommandStack() {
		return commandStack;
	}
}
